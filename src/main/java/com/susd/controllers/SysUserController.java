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

import com.susd.application.SysUserService;
import com.susd.domain.identity.SysRole;
import com.susd.domain.identity.SysRoleRepository;
import com.susd.domain.identity.SysUser;
import com.susd.domain.identity.SysUserRepository;
import com.susd.domainservice.identity.SessionManager;
import com.susd.dto.UserDto;
import com.susd.infrastructure.DatatableParam;
import com.susd.infrastructure.DatatableResult;
import com.susd.infrastructure.OptResult;

@RequestMapping(value = "/sysuser")
@Controller
public class SysUserController{

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private SysRoleRepository sysRoleRepositoty;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Map<String, Object> map) {

//		System.out.println(request.getRequestURL().toString());
//		System.out.println(request.getRequestURI());
//		
//		StringBuffer url = request.getRequestURL();  
//		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();  
//
//		System.out.println(tempContextUrl);
		
		List<SysRole> roles=sysRoleRepositoty.findByKeyword(null,SessionManager.getTenantId());
		map.put("roles", roles);
		
		return "sysuser/index";
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
	public DatatableResult<UserDto> query(HttpServletRequest request, DatatableParam param, String keyword) {

		DatatableResult<UserDto> result = sysUserService.queryByKeyword(keyword, param);

		return result;
	}

	@RequestMapping(value = { "/edit/{id}", "/edit" }, method = RequestMethod.GET)
	public String edit(Map<String, Object> map,
			@PathVariable(name = "id", required = false) Integer id) {
		if (id!=null && id > 0) {
			SysUser user = sysUserRepository.findUserById(id);
			map.put("model", user);
		}
		return "sysuser/edit";
	}

	/**
	 * 保存数据
	 * 
	 * @param request
	 * @param user    用户信息
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public OptResult save(HttpServletRequest request, SysUser user) {

		return sysUserService.save(user);
	}

	/**
	 * 重置密码
	 * 
	 * @param request
	 * @param userId    用户编号
	 * @return 返回保存的结果
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public OptResult reset(HttpServletRequest request,int userId) {
		return sysUserService.resetPwd(userId);
	}
	
	/**
	 * 启用/冻结
	 * @param request
	 * @param userId 用户编号
	 * @param opType 操作类型 1：启用 10：冻结
	 * @return
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public OptResult change(HttpServletRequest request,int userId,int opType) {
		return sysUserService.chgStatus(userId, opType);
	}

	/**
	 * 保存角色 
	 * @param userId
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/saverole", method = RequestMethod.POST)
	@ResponseBody
	public OptResult saverole(int userId,String[] role) {

		OptResult result=sysUserService.saveRole(userId, role);
		
		return result;
	}
	@RequestMapping(value = "/chgPwd", method = RequestMethod.POST)
	@ResponseBody
	public OptResult changePassword(String oldPwd, String newPwd, String confirmPwd) {
		return sysUserService.changePassword(oldPwd, newPwd);
	}
}
