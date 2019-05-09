package com.susd.application.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.susd.application.PriceConfigService;
import com.susd.domain.complex.PriceConfig;
import com.susd.domain.complex.PriceConfigRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;
import com.susd.infrastructure.Validate;

@Service
public class PriceConfigServiceImpl implements PriceConfigService {

	@Autowired
	private PriceConfigRepository priceConfigRepository;

	@Override
	public DatatableResult<PriceConfig> queryByKeyword(String keyword, DatatableParam param) {

		PageHelper.startPage(param.getPageIndex(), param.getLength(), true);
		List<PriceConfig> priceConfigs = priceConfigRepository.findByKeyword(keyword,SessionManager.getTenantId());

		PageInfo<PriceConfig> pageInfo = new PageInfo<PriceConfig>(priceConfigs);

		return new DatatableResult<PriceConfig>(pageInfo, param.getDraw());
	}

	@Override
	public OptResult save(PriceConfig priceConfig) {
		if (Validate.isValid(priceConfig)) {

			int res = 0;
			if (priceConfig.getId() == 0) {
				priceConfig.setVersion(1);
				priceConfig.setAddTime(new Date());
				priceConfig.setTenantId(SessionManager.getTenantId());//所属租户编号
				priceConfig.setMaintainId(SessionManager.getUserId());//维护人员编号
				res = priceConfigRepository.add(priceConfig);
			} else {
				PriceConfig old = priceConfigRepository.findPriceConfigById(priceConfig.getId());
				if (old.getVersion() != priceConfig.getVersion()) {
					return OptResult.Failed("信息已修改过，请刷新后重新修改");
				}
				res = priceConfigRepository.update(priceConfig);
			}
			if (res > 0)
				return OptResult.Successed();
			return OptResult.Failed("信息保存失败，请稍候重试");
		}

		return Validate.verify(priceConfig);
	}

	
	@Override
	public OptResult delete(int priceConfigId) {
		PriceConfig priceConfig = priceConfigRepository.findPriceConfigById(priceConfigId);
	
		if (priceConfig == null || priceConfigId == 0)
			return OptResult.Failed("数据不存在");

		int res = priceConfigRepository.delete(priceConfigId);

		if (res > 0)
			return OptResult.Successed();
		return OptResult.Failed("失败，请稍候重试");

	}

}
