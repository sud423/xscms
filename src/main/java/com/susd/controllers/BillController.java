package com.susd.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.susd.domainservice.identity.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.BillService;
import com.susd.domain.bill.BillItem;
import com.susd.domain.bill.BillItemRepository;
import com.susd.domain.complex.Dict;
import com.susd.domain.complex.DictRepository;
import com.susd.dto.BillDto;
import com.susd.dto.BillItemInputDto;
import com.susd.dto.BillItemOutDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/bill")
@Controller
public class BillController {

	@Autowired
	private BillService billService;
	@Autowired 
	private BillItemRepository billItemRepository;
	@Autowired
	private DictRepository dictRepository;
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "bill/index";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search() {
		return "bill/search";
	}
	/**
	 * 获取账单列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<BillDto> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<BillDto> result = billService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
		if (id!=null&& id > 0) {
			BillItem bill=billItemRepository.findById(id);
			map.put("model",bill);
		}
		
		List<Dict> dataSource=dictRepository.findDictByKey("express","", SessionManager.getTenantId());
		map.put("data",dataSource);
		
		return "bill/edit";
	}
	
	/**
	 * 运费计算
	 * 
	 * @param request 当前HTTP请求
	 * @return
	 */
	@RequestMapping(value = "/countFee", method = RequestMethod.POST)
	@ResponseBody
	public BillItemInputDto countFee(HttpServletRequest request,BillItemInputDto input) {
		return billService.countFee(input);
	}
	
	/**
	 * 保存账单
	 * 
	 * @param request 当前HTTP请求
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(BillItemInputDto input) {
		return billService.save(input);
	}
	
	/**
	 * 保存账单
	 * 
	 * @param request 当前HTTP请求
	 * @return
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	@ResponseBody
	public OptResult push(int id) {
		return billService.push(id);
	}
	
	/**
	 * 获取账单项列表
	 * @param billId
	 * @return
	 */
	@RequestMapping(value = "/getItem", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<BillItemOutDto> findBillItems(DatatableParam param,int billId){
		return billService.findBillItems(param,billId);
	}
	
}
