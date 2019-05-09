package com.susd.application;

import com.susd.domain.identity.SysUser;
import com.susd.dto.UserDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface SysUserService {

	/**
	 * 关键字查询 用户
	 * @param keyword 关键字
	 * @param page 索引页，下标从1开始
	 * @param size 当前显示几条记录
	 * @return
	 */
	DatatableResult<UserDto> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 保存用户信息
	 * @param user 待存储用户信息
	 * @return
	 */
	OptResult save(SysUser user);
	
	/**
	 * 重置密码
	 * @param userId 用户编号
	 * @return
	 */
	OptResult resetPwd(int userId);
	
	/**
	 * 启用/冻结账户
	 * @param userId 用户编号
	 * @param opType 操作类型：1：启用 10：冻结
	 * @return
	 */
	OptResult chgStatus(int userId, int opType);
	
	/**
	 * 保存配置角色
	 * @param userId 用户编号 
	 * @param roles 角色编号列表
	 * @return
	 */
	OptResult saveRole(int userId, String[] roles);
	
	/**
	 * 更换密码
	 * @param currentPassword
	 * @param newPassword
	 * @return
	 */
	OptResult changePassword(String currentPassword, String newPassword);
}
