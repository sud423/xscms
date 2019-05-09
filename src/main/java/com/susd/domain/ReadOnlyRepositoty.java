package com.susd.domain;

import java.util.Collection;

/**
 * @description 只读仓储
 * @author sushu
 *
 * @param <TEntity> 实体泛型
 * @param <TKey> 实体主键类型
 */
public interface ReadOnlyRepositoty<TEntity,TKey> {

	/**
	 * @description 判断记录是否存在
	 * @param id 主键值
	 * @return 如果存在返回true，否则 返回false
	 */
	boolean contains(TKey id);
	
	/**
	 * @description 根据主键值获取实体
	 * @param id 主键值
	 * @return 返回实体
	 */
	TEntity get(TKey id);
	
	/**
	 * @description 获取所有记录
	 * @return 返回查询结果集
	 */
	Collection<TEntity> getAll();
}
