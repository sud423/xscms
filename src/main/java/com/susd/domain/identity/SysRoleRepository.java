package com.susd.domain.identity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleRepository {

	/**
	 * 添加角色
	 * @param role 待添加角色 信息
	 * @return 返回受影响行
	 */
	int add(SysRole role);
	
	/**
	 * 根据关键字查询角色列表
	 * @param keyword 关键字
	 */
	List<SysRole> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
		
	/**
	 * 根据主键获取角色信息
	 * 
	 * @param roleId 角色编号
	 * @return SysRole 角色信息
	 */
	SysRole findRoleById(int roleId);
	
	/**
	 * 更新用户信息
	 * @param user 待更新用户
	 * @return 返回受影响行数
	 */
	int update(SysRole user);
	
	
	/**
	 * 根据角色删除配置权限信息
	 * @param roleId 角色编号
	 * @return
	 */
	int deletePermission(int roleId);
	
	/**
	 * 保存角色权限
	 * @param roleId 角色编号
	 * @param permissionIds 权限列表
	 * @return
	 */
	int savePermission(@Param("roleId") int roleId, @Param("permissionIds") String[] permissionIds);
}
