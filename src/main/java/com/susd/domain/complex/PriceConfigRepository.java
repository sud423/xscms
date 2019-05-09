package com.susd.domain.complex;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PriceConfigRepository {

	/**
	 * 添加运费配置
	 * 
	 * @param priceConfig 待添加的运费配置信息
	 * @return 返回受影响行数
	 */
	int add(PriceConfig priceConfig);

	/**
	 * 根据关键字查询运费配置列表
	 * 
	 * @param keyword 关键字
	 */
	List<PriceConfig> findByKeyword(@Param("keyword") String keyword, @Param("tenantId") int tenantId);

	/**
	 * 根据主键获取运费配置信息
	 * 
	 * @param priceConfigId 运费配置编号
	 * @return PriceConfig 运费配置信息
	 */
	PriceConfig findPriceConfigById(int priceConfigId);

	/**
	 * 更新运费配置信息
	 * 
	 * @param priceConfig 待更新运费配置
	 * @return 返回受影响行数
	 */
	int update(PriceConfig priceConfig);

	/**
	 * 删除单条数据
	 * 
	 * @param priceConfigId 待删除运费配置id
	 * @return
	 */
	int delete(int priceConfigId);

	/**
	 * 运费查询 计算
	 * 
	 * @param province
	 * @param city
	 * @param volume
	 * @param weight
	 * @return
	 */
	List<PriceSearchResult> priceCount(@Param("province") String province, @Param("city") String city,
									   @Param("volume") float volume, @Param("weight") float weight, @Param("tenantId") int tenantId);

	/**
	 * 根据目的地及快递名称根据运费配置
	 * 
	 * @param province 省
	 * @param city     市
	 * @param express  快递
	 * @return
	 */
	PriceConfig findPrice(@Param("province") String province, @Param("city") String city,
						  @Param("express") String express, @Param("tenantId") int tenantId);
}
