package com.susd.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.PriceConfigService;
import com.susd.domain.complex.Dict;
import com.susd.domain.complex.DictRepository;
import com.susd.domain.complex.PriceConfig;
import com.susd.domain.complex.PriceConfigRepository;
import com.susd.domain.complex.PriceSearchResult;
import com.susd.domainservice.identity.SessionManager;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/priceconfig")
@Controller
public class PriceConfigController {

	@Autowired
	private PriceConfigService priceConfigService;
	@Autowired
	private PriceConfigRepository priceConfigRepository;
	@Autowired
	private DictRepository dictRepository;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "priceconfig/index";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {
		return "priceconfig/search";
	}

	/**
	 * 获取运费配置列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<PriceConfig> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<PriceConfig> result = priceConfigService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map, @PathVariable(name = "id", required = false) Integer id) {
		if (id != null && id > 0) {
			PriceConfig priceConfig = priceConfigRepository.findPriceConfigById(id);
			map.put("model", priceConfig);
		}

		List<Dict> dataSource = dictRepository.findDictByKey("express");
		map.put("data", dataSource);

		return "priceconfig/edit";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param priceConfig 运费配置信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, PriceConfig priceConfig) {

		return priceConfigService.save(priceConfig);
	}

	/**
	 * 删除运费配置
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public OptResult delete(HttpServletRequest request, int priceConfigId) {
		return priceConfigService.delete(priceConfigId);
	}

	/**
	 * 运费查询
	 * 
	 * @param province 省份
	 * @param city     市
	 * @param volume   体积
	 * @param weight   重量
	 * @return
	 */
	@RequestMapping(value = "/count", method = RequestMethod.POST)
	@ResponseBody
	public List<PriceSearchResult> priceCount(String province, String city, float volume, float weight) {
		return priceConfigRepository.priceCount(province, city, volume, weight, SessionManager.getTenantId());
	}
}
