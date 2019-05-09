package com.susd.application;

import com.susd.domain.training.Cla;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface ClaService {
	/**
	 * 根据关键字查询班级
	 * @param keyword
	 * @return
	 */
	DatatableResult<Cla> findByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 标记项目为删除
	 * @param id
	 * @return
	 */
	OptResult delete(int id);
	
	/**
	 * 保存数据
	 * @param cla
	 * @return
	 */
	OptResult save(Cla cla);
}
