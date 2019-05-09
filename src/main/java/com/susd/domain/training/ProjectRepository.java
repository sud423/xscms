package com.susd.domain.training;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface ProjectRepository {
	/**
	 * 根据主键获取项目信息
	 * 
	 * @param projectId 项目编号
	 * @return 项目对象信息
	 */
	Project findProjectById(int projectId);
	
	/**
	 * 根据关键字查询项目列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<Project> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param project
	 * @return
	 */
	int add(Project project);
	
	/**
	 * 更新
	 * @param project
	 * @return
	 */
	int update(Project project);
}
