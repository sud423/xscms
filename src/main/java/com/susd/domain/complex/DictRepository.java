package com.susd.domain.complex;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DictRepository {
	/**
	 * 根据主键获取字典对象信息
	 * 
	 * @param dictId 字典编号
	 * @return 字典对象
	 */
	Dict findDictById(int dictId);
	
	/**
	 * 根据字段键查询 字典列表
	 * @param key
	 * @return
	 */
	List<Dict> findDictByKey(@Param("key") String key);
	
}
