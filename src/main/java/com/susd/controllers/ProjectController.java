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

import com.susd.application.ProjectService;
import com.susd.domain.training.Project;
import com.susd.domain.training.ProjectRepository;
import com.susd.dto.SelectDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/project")
@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectRepository projectRepository;
	
	/**
	 * 项目管理首页
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "project/index";
	}
	
	/**
	 * 获取车辆列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<Project> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<Project> result = projectService.findByKeyword(keyword, param);

		return result;
	}
	
	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
				if (id!=null&& id > 0) {
					Project project=projectRepository.findProjectById(id);
					map.put("model",project);
		}
		return "project/edit";
	}
	
	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param project    项目信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, Project project) {

		return projectService.save(project);
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
		return projectService.delete(id);
	}
	
	/**
	 * 获取项目
	 * @return
	 */
	@RequestMapping(value = "/getall", method = RequestMethod.GET)
	@ResponseBody
	public List<SelectDto> getAll(String q){
		List<SelectDto> drivers =projectService.getAll(q);
		
		return drivers;
	}
}
