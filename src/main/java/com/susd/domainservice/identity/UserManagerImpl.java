package com.susd.domainservice.identity;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.susd.domain.identity.SysUserRepository;
import com.susd.infrastructure.OptResult;

@Service
public class UserManagerImpl implements UserManager {


	@Autowired
	private SysUserRepository sysUserRepository;
	
	/**
	 * 登录
	 * 
	 * @param userName   用户名
	 * @param password   密码
	 * @param rememberMe 记住我
	 * @return 返回身份验证结果
	 */
	public OptResult signIn(String userName, String password, boolean rememberMe) {
		
		if(userName==null)
			return OptResult.Failed("用户名不能为空");
		
		if(password==null)
			return OptResult.Failed("密码不能为空");
			
		
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		token.setRememberMe(rememberMe);

		Subject subject = SecurityUtils.getSubject();
		try {

			subject.login(token);

			if (subject.isAuthenticated()) {
				//更新
				sysUserRepository.updateLastLoginTime(userName);
				
				return OptResult.Successed();
			} else {
				return OptResult.Failed("用户名和密码不匹配！");
			}

		} catch (IncorrectCredentialsException e) {
			System.out.println(e);
			return OptResult.Failed("用户名和密码不匹配！");
		} catch (ExcessiveAttemptsException e) {
			System.out.println(e);
			return OptResult.Failed("由于您登录的失败次数已超过5次，账户已被锁定，请10分钟后再试！");
		} catch (LockedAccountException e) {
			System.out.println(e);
			return OptResult.Failed("您的账户已冻结，请与管理员联系！");
		} catch (DisabledAccountException e) {
			System.out.println(e);
			return OptResult.Failed("您的账户已禁用，请与管理员联系！");
		} catch (ExpiredCredentialsException e) {// 身份已过期
			System.out.println(e);
			return OptResult.Failed("身份已过期！");
		} catch (UnknownAccountException e) {// 未知的账号
			System.out.println(e);
			return OptResult.Failed("账号和密码不匹配！");
		} catch (UnauthorizedException e) {// 未知的授权
			System.out.println(e);
			return OptResult.Failed("用户名和密码不匹配！");
		}

	}

	/**
	 * 登出
	 */
	public void signOut() {
		// shiro登出清理凭证信息
		SecurityUtils.getSubject().logout();
	}
}
