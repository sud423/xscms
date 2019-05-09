package com.susd.application;

import com.susd.domain.identity.SysRole;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface SysRoleService {

	/**
	 * 关键字查询角色列表
	 * @param keyword 关键字
	 * @param page 索引页，下标从1开始
	 * @param size 当前显示几条记录
	 * @return
	 */
	DatatableResult<SysRole> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 保存用户信息
	 * @param user 待存储用户信息
	 * @return
	 */
	OptResult save(SysRole role);
	
	/**
	 * 保存权限信息
	 * @param roleId 角色编号
	 * @param permissionIds 权限列表
	 * @return
	 */
	OptResult savePermission(int roleId, String[] permissionIds);
}
