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
import com.susd.application.SysRoleService;
import com.susd.domain.identity.SysRole;
import com.susd.domain.identity.SysRoleRepository;
import com.susd.dto.TreeDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@Controller
@RequestMapping(value = "/sysrole")
public class SysRoleController {
	
	@Autowired
	private SysRoleRepository sysRoleRepository;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Map<String, Object> map,HttpServletRequest request) {

		List<TreeDto> dataSource=resourceService.queryToDropDataSource(SessionManager.getTenantId());
		
		String json=JSONObject.toJSONString(dataSource);
		
		map.put("datasource", json);
		return "sysrole/index";
	}

	/**
	 * 获取用户列表
	 * 
	 * @param request 当前HTTP请求
	 * @param keyword 关键字
	 * @param page    索引页，下标从1开始
	 * @param size    当前显示几条记录
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public DatatableResult<SysRole> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<SysRole> result = sysRoleService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
		if (id!=null && id > 0) {
			SysRole user = sysRoleRepository.findRoleById(id);
			map.put("model", user);
		}
		return "sysrole/edit";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param role    角色信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, SysRole role) {

		return sysRoleService.save(role);
	}

	/**
	 * 保存授权
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	@RequestMapping(value = "/savepermission", method = RequestMethod.POST)
	@ResponseBody
	public OptResult savePermission(int roleId,String permissionId) {
		if(permissionId==null || permissionId=="")
			return OptResult.Failed("请勾选权限");
		
		String [] permissionIds=permissionId.split(",");
		
		return sysRoleService.savePermission(roleId, permissionIds);
	}
}
