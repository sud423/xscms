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

import com.alibaba.fastjson.JSONObject;
import com.susd.application.ResourceService;
import com.susd.domain.identity.Resource;
import com.susd.domain.identity.ResourceRepository;
import com.susd.dto.TreeDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController {
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ResourceService resourceService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {

		return "resource/index";
	}

	/**
	 * 获取用户列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<Resource> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<Resource> result = resourceService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,@PathVariable(name = "id", required = false) Integer id) {
		if (id!=null && id > 0) {
			Resource user = resourceRepository.findResourceById(id);
			map.put("model", user);
		}
		
		List<TreeDto> dataSource=resourceService.queryToDropDataSource(0);
		
		String json=JSONObject.toJSONString(dataSource);
		
		map.put("datasource", json);
		
		return "resource/edit";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param resource    资源信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, Resource resource) {

		return resourceService.save(resource);
	}
}
