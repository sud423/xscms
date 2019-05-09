package com.susd.domain.identity;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

public interface SysUserRepository {

	/**
	 * 添加用户
	 * @param user 待添加的用户信息
	 * @return 返回受影响行数
	 */
	int add(SysUser user);
	
	/**
	 * 根据关键字查询管理员列表
	 * @param keyword 关键字
	 */
	List<SysUser> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	
	/**
	 * 根据主键获取用户信息
	 * 
	 * @param userId 用户编号
	 * @return User 用户信息
	 */
	SysUser findUserById(int userId);

	/**
	 * 根据用户名获取用户信息
	 * 
	 * @param userId 用户名
	 */
	SysUser findByUserName(String userName);

	/**
	 * 根据用户名查询相应的角色
	 * 
	 * @param userName 用户名
	 * @return 返回用户名相应的角色
	 */
	Set<String> findRoleByUserName(@Param("userName") String userName, @Param("tenantId") int tenantId);

	/**
	 * 根据用户名查询相应的权限
	 * 
	 * @param userName 用户名
	 * @return 返回用户名相应的资源权限
	 */
	Set<String> findResourceByUserName(@Param("userName") String userName, @Param("tenantId") int tenantId);

	/**
	 * 更新用户信息
	 * @param user 待更新用户
	 * @return 返回受影响行数
	 */
	int update(SysUser user);
		
	/**
	 * 登录成功后更新最后登录时间
	 * 
	 * @param userName 当前登录的用户名
	 */
	void updateLastLoginTime(String userName);
	
	/**
	 * 删除用户对应的角色
	 * @param userId 用户编号
	 * @return
	 */
	int deleteRole(int userId);
	
	/**
	 * 
	 * @param userId
	 * @param roles
	 * @return
	 */
	int saveRole(@Param("userId") int userId, @Param("roles") String[] roles);
	
}
