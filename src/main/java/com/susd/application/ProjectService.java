package com.susd.application;

import java.util.List;

import com.susd.domain.training.Project;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ProjectService {
	/**
	 * 根据关键字查询项目
	 * @param keyword
	 * @return
	 */
	DatatableResult<Project> findByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 标记项目为删除
	 * @param id
	 * @return
	 */
	OptResult delete(int id);
	
	/**
	 * 保存数据
	 * @param project
	 * @return
	 */
	OptResult save(Project project);
	
	/**
	 * 获取所有项目列表
	 * @return
	 */
	List<SelectDto> getAll(String keyword);
}
