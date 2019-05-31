package com.susd.application.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.susd.domainservice.identity.UserRealm;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.SysUserService;
import com.susd.domain.identity.SysUser;
import com.susd.domain.identity.SysUserRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.UserDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private UserRealm userRealm;

	@Override
	public DatatableResult<UserDto> queryByKeyword(String keyword, DatatableParam param) {

		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<SysUser> users = sysUserRepository.findByKeyword(keyword,SessionManager.getTenantId());
		PageInfo<SysUser> pageuInfo = new PageInfo<SysUser>(users);
		
		ModelMapper modelMapper = new ModelMapper();

		Converter<ArrayList<SysUser>, ArrayList<UserDto>> converter = new AbstractConverter<ArrayList<SysUser>, ArrayList<UserDto>>() {

			@Override
			protected ArrayList<UserDto> convert(ArrayList<SysUser> source) {

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
				modelMapper.addMappings(userMap);
				return modelMapper.map(source, new TypeToken<ArrayList<UserDto>>() {
				}.getType());
			}
		};
		PropertyMap<PageInfo<SysUser>, PageInfo<UserDto>> propertyMap = new PropertyMap<PageInfo<SysUser>, PageInfo<UserDto>>() {
			@Override
			protected void configure() {
				using(converter).map(source.getList(), destination.getList());
			}
		};
		modelMapper.addMappings(propertyMap);
		

		
		PageInfo<UserDto> pageInfo = modelMapper.map(pageuInfo, new TypeToken<PageInfo<UserDto>>() {}.getType());

		return new DatatableResult<UserDto>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult save(SysUser user) {
		if (Validate.isValid(user)) {

			int res = 0;
			if (user.getId() == 0) {
				// 随机数生成器
				RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
				user.setSalt(randomNumberGenerator.nextBytes().toHex());
				String newPassword = new SimpleHash("md5", "qwe123", user.getSalt(), 2).toHex();
				user.setPassword(newPassword);
				user.setVersion(1);
				user.setAddTime(new Date());
				user.setStatus((byte) 1);
				user.setTenantId(SessionManager.getTenantId());
				res = sysUserRepository.add(user);
			} else {
				SysUser old = sysUserRepository.findUserById(user.getId());
				if (old.getVersion() != user.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				user.setPassword(old.getPassword());
				user.setStatus(old.getStatus());
				res = sysUserRepository.update(user);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(user);
	}

	@Override
	public OptResult resetPwd(int userId) {

		SysUser user = sysUserRepository.findUserById(userId);
		if (user == null || user.getId() == 0)
			return OptResult.Failed("待重置的账户不存在");

		String newPassword = new SimpleHash("md5", "qwe123", user.getSalt(), 2).toHex();
		user.setPassword(newPassword);

		int res = sysUserRepository.update(user);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("密码重置失败，请稍候重试");

	}

	@Override
	public OptResult chgStatus(int userId, int opType) {
		SysUser user = sysUserRepository.findUserById(userId);
		String msg;
		if (opType == 1) {
			user.enable();
			msg = "启用";
		} else if (opType == 10) {
			user.freeze();

			msg = "冻结";
		} else
			return OptResult.Failed("提交失败，非法的操作");
		

		if (user == null || user.getId() == 0)
			return OptResult.Failed(msg + "账户不存在");

		int res = sysUserRepository.update(user);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed(msg + "失败，请稍候重试");

	}

	@Override
	public OptResult saveRole(int userId, String[] roles) {

		if (roles == null || roles.length == 0)
			return OptResult.Failed("角色不能为空");

//		List<String> list=Arrays.asList(roles);
		
		sysUserRepository.deleteRole(userId);
		
		int res = sysUserRepository.saveRole(userId, roles);
		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("角色配置失败");
	}

	@Override
	public OptResult changePassword(String currentPassword, String newPassword) {
		SysUser user = sysUserRepository.findUserById(SessionManager.getUserId());
		

		String crtPwd = new SimpleHash("md5", currentPassword, user.getSalt(), 2).toHex();
		
		if(!crtPwd.equals(user.getPassword()))
			return OptResult.Failed("原密码不正确，请重新输入");
		
		String newPwd=new SimpleHash("md5", newPassword, user.getSalt(), 2).toHex();
		user.setPassword(newPwd);

		int res = sysUserRepository.update(user);

		if (res > 0){
			userRealm.clearCache();
			return OptResult.Successed();
		}
		return OptResult.Failed("密码修改失败，请稍候重试");
	}

}
