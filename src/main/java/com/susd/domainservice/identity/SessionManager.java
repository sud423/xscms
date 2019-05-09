package com.susd.domainservice.identity;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.susd.dto.UserDto;

/**
 * 用户身份会话管理
 * @author TY
 *
 */
public class SessionManager {

	/**
	 * 获取用户编号
	 * @return
	 */
	public static int getUserId() {
		UserDto user=getUser();
		if(user==null) return 0;
		return user.getId();
	}
	
	public static int getTenantId() {
		UserDto user=getUser();
		if(user==null) return 0;
		return user.getTenantId();
	}
	
	/**
	 * 获取用户信息对象
	 * @return
	 */
	public static UserDto getUser() {
		Subject currentUser = SecurityUtils.getSubject();

		Session session = currentUser.getSession();
		Object obj=session.getAttribute("user");
		if(obj==null)
			return null;
		
		return (UserDto)obj;
	}
	
}
