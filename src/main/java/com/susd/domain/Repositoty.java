package com.susd.domain;

public interface Repositoty<TEntity, TKey> extends ReadOnlyRepositoty<TEntity, TKey> {

	/**
	 * @description 添加实体
	 * @param entity 待添加实体
	 */
	void add(TEntity entity);
	
	/**
	 * @description 更新实体
	 * @param entity 待更新实体
	 */
	void update(TEntity entity);
	
	/**
	 * @description 根据主键值删除数据
	 * @param id 主键编号
	 */
	void remove(TKey id);
}
