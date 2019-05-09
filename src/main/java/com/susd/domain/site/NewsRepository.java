package com.susd.domain.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NewsRepository {

	/**
	 * 根据主键获取单条新闻信息
	 * 
	 * @param newsId 新闻编号
	 * @return 新闻信息
	 */
	News findNewsById(int newsId);
	
	/**
	 * 根据关键字查询事件列表
	 * @param keyword
	 * @param tenantId
	 * @return
	 */
	List<News> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);
	
	/**
	 * 新增 
	 * @param news
	 * @return
	 */
	int add(News news);
	
	/**
	 * 更新
	 * @param news
	 * @return
	 */
	int update(News news);
}
