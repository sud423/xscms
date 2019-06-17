package com.susd.domainservice.identity;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;


import com.susd.domain.identity.SysUser;
import com.susd.domain.identity.SysUserRepository;
import com.susd.dto.UserDto;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SysUserRepository sysUserRepository;

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName("userRealm");
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserDto user = (UserDto) principals.getPrimaryPrincipal();
		String username = user.getUserName();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roles = sysUserRepository.findRoleByUserName(username, user.getTenantId());
		authorizationInfo.setRoles(roles);
		Set<String> resources = sysUserRepository.findResourceByUserName(username);
		authorizationInfo.setStringPermissions(resources);
		
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		SysUser user = sysUserRepository.findByUserName(username);
		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}
		if (user.getStatus() == 20) {
			throw new LockedAccountException(); // 帐号锁定
		}

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();

		PropertyMap<SysUser, UserDto> userMap = new PropertyMap<SysUser, UserDto>() {
			@Override
			protected void configure() {
				try {
					map().setCreated(source.getAddTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(userMap);

		UserDto userDto = modelMapper.map(user, UserDto.class);

		session.setAttribute("user", userDto);

		return new SimpleAuthenticationInfo(userDto, user.getPassword(), ByteSource.Util.bytes(user.getSalt()),
				getName());

	}

	public static void main(String[] args) {
		// 1. 创建缓存管理器
		CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
		// 2. 获取缓存对象
		Cache cache = cacheManager.getCache("authorizationInfoCache");
		// 3. 创建元素
		Element element = new Element("key1", "value1");
		// 4. 将元素添加到缓存
		cache.put(element);
		// 5. 获取缓存
		Element value = cache.get("key1");
		System.out.println(value);
		System.out.println(value.getObjectValue());

		System.out.println(new Date().getTime() / 1000);
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
