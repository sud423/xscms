package com.susd.application;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.susd.domain.site.User;
import com.susd.dto.ClientDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ClientService {

	/**
	 * 新增 / 修改
	 * @param user 待保存信息
	 * @return 返回json对象
	 */
	OptResult save(User user);
	
	/**
	 * 根据关键字分布查询客户列表
	 * @param keyword 关键字
	 * @param param 分页参数
	 * @return 返回查询 的结果集
	 */
	DatatableResult<ClientDto> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 审核
	 * @param userId 客户编号
	 * @param status 状态
	 * @param reason 审核不通过原因
	 * @return
	 */
	OptResult audit(int userId, byte status, String reason);
	
	/**
	 * 获取客户
	 * @param q
	 * @return
	 */
	List<SelectDto> findClient(@Param("q") String q);
}
