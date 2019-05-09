package com.susd.domain.training;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClaRepository {
	/**
	 * 根据主键获取班级信息
	 * 
	 * @param projectId 班级编号
	 * @return 班级对象信息
	 */
	Cla findClaById(int claId);
	
	/**
	 * 根据关键字查询班级列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<Cla> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param cla
	 * @return
	 */
	int add(Cla cla);
	
	/**
	 * 更新
	 * @param cla
	 * @return
	 */
	int update(Cla cla);
}
