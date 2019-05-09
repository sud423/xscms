package com.susd.application;

import com.susd.domain.training.PersonnelInfo;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface PersonnelInfoService {
	/**
	 * 根据关键字查询学员
	 * @param keyword
	 * @return
	 */
	DatatableResult<PersonnelInfo> findByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 标记学员为删除
	 * @param id
	 * @return
	 */
	OptResult delete(int id);
	
	/**
	 * 保存数据
	 * @param cla
	 * @return
	 */
	OptResult save(PersonnelInfo info);
}
