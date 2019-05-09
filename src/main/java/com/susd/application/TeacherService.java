package com.susd.application;

import com.susd.domain.training.Teacher;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface TeacherService {
	/**
	 * 根据关键字查询师资
	 * @param keyword
	 * @return
	 */
	DatatableResult<Teacher> findByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 标记师资为删除
	 * @param id
	 * @return
	 */
	OptResult delete(int id);
	
	/**
	 * 保存数据
	 * @param teacher
	 * @return
	 */
	OptResult save(Teacher teacher);
}
