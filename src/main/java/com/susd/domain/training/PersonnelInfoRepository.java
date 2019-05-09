package com.susd.domain.training;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PersonnelInfoRepository {
	/**
	 * 根据主键获取学员信息
	 * 
	 * @param id 学员编号
	 * @return 学员对象信息
	 */
	PersonnelInfo findPersonnelInfoById(int id);
	
	/**
	 * 根据关键字查询学员列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<PersonnelInfo> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param info
	 * @return
	 */
	int add(PersonnelInfo info);
	
	/**
	 * 更新
	 * @param info
	 * @return
	 */
	int update(PersonnelInfo info);
}
