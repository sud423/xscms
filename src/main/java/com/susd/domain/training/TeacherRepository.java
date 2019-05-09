package com.susd.domain.training;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TeacherRepository {
	/**
	 * 根据主键获取师资信息
	 * 
	 * @param id 师资编号
	 * @return 师资对象信息
	 */
	Teacher findTeacherById(int id);
	
	/**
	 * 根据关键字查询师资列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<Teacher> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param teacher
	 * @return
	 */
	int add(Teacher teacher);
	
	/**
	 * 更新
	 * @param teacher
	 * @return
	 */
	int update(Teacher teacher);
}
