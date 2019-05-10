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
	 * 新增字典
	 * @param dict 待添加字典
	 * @return
	 */
	int add(Dict dict);

	/**
	 * 更新字典
	 * @param dict 待更新字典
	 * @return
	 */
	int update(Dict dict);

	/**
	 * 根据字段键查询 字典列表
	 * @param key 字典键
	 * @param keyword 关键字查询
	 * @param tenantId 所属租户
	 * @return
	 */
	List<Dict> findDictByKey(@Param("key") String key,@Param("keyword") String keyword,@Param("tenantId") int tenantId);
	
}
