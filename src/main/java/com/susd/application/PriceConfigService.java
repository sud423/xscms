package com.susd.application;

import com.susd.domain.complex.PriceConfig;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

public interface PriceConfigService {

	/**
	 * 关键字查询运费配置信息
	 * @param keyword 关键字
	 * @param page 索引页，下标从1开始
	 * @param size 当前显示几条记录
	 * @return
	 */
	DatatableResult<PriceConfig> queryByKeyword(String keyword, DatatableParam param);
	
	/**
	 * 保存运费配置信息
	 * @param user 待存储运费配置信息
	 * @return
	 */
	OptResult save(PriceConfig priceConfig);
	
	/**
	 * 删除运费配置
	 * @param priceConfigId 运费配置编号
	 * @return
	 */
	OptResult delete(int priceConfigId);
}
