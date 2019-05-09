package com.susd.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.ClientService;
import com.susd.dto.ClientDto;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/client")
@Controller
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "client/index";
	}
	/**
	 * 获取客户列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<ClientDto> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<ClientDto> result = clientService.queryByKeyword(keyword, param);

		return result;
	}

	/**
	 * 审核客户信息
	 * @param userId 驾驶员Id
	 * @param status 状态1：审核通过；30：审核不通过
	 * @param auditReason
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public OptResult audit(int userId,byte status,String auditReason) {
		
		OptResult res=clientService.audit(userId, status, auditReason);
		
		return res;
		
	}
	/**
	 * 获取客户
	 * @param q
	 * @return
	 */
	@RequestMapping(value = "/findClient", method = RequestMethod.GET)
	@ResponseBody
	public List<SelectDto> findClient(String q){
		return clientService.findClient(q);
	}
}
