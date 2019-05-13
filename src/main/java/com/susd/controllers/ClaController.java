package com.susd.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.application.ClaService;
import com.susd.domain.training.Cla;
import com.susd.domain.training.ClaRepository;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@Controller
@RequestMapping(value = "/cla")
public class ClaController {

	@Autowired
	private ClaService claService;
	
	@Autowired
	private ClaRepository claRepository;
	
	/**
	 * 项目管理首页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "cla/index";
	}
	
	/**
	 * 获取车辆列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param param    分页对象信息
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<Cla> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<Cla> result = claService.findByKeyword(keyword, param);

		return result;
	}
	
	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					Cla cla=claRepository.findClaById(id);
					map.put("model",cla);
		}
		return "cla/edit";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param cla    班级信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, Cla cla) {

		return claService.save(cla);
	}
	
	/**
	 * 删除项目
	 * @param request
	 * @param id 项目编号
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult change(HttpServletRequest request,int id) {
		return claService.delete(id);
	}
}
