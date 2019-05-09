package com.susd.domain.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface CourseRepository {
	/**
	 * 根据主键获取课程信息
	 * 
	 * @param courseId 课程编号
	 * @return 课程对象信息
	 */
	Course findCourseById(int courseId);
	
	/**
	 * 根据关键字查询事件列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<Course> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param news
	 * @return
	 */
	int add(Course news);
	
	/**
	 * 更新
	 * @param news
	 * @return
	 */
	int update(Course news);
}
