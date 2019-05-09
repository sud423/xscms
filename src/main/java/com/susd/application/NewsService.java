package com.susd.application;

import com.susd.domain.site.News;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface NewsService {
	/**
	 * 根据关键字查询活动
	 * @param keyword
	 * @return
	 */
	DatatableResult<News> findByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 标记活动为删除
	 * @param id
	 * @return
	 */
	OptResult delete(int id);
	
	/**
	 * 保存数据
	 * @param ann
	 * @return
	 */
	OptResult save(News news);
}
