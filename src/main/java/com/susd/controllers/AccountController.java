package com.susd.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.susd.domainservice.identity.UserManager;
import com.susd.infrastructure.OptResult;


@Controller
public class AccountController {

	@Autowired
	private UserManager userManager;

	/**
	 * 登录页面
	 * 
	 * @return 返回视图页
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {

//		EhcacheManager.getInstance().put("authorizationInfoCache", "key1", "value1");
//		Object objectValue = EhcacheManager.getInstance().get("authorizationInfoCache", "key1");
//		Element value = EhcacheManager.getInstance().getCache("authorizationInfoCache").get("key1");
//		System.out.println(objectValue);
//		System.out.println(value);
//		System.out.println(value.getObjectValue());
//		if (request.getHeader("x-requested-with") != null
//				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
//			return JSON.toJSONString(OptResult.Failed(500403, "登录身份已过期"));
//		} else
			return "account/index";
	}

	/**
	 * 登录方法
	 * 
	 * @return 返回json格式的登录结果
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public OptResult logon(HttpServletRequest request, String userName, String password, String rememberMe) {

		boolean remember = false;
		if (rememberMe != null) {
			remember = rememberMe.toLowerCase() == "true";
		}

		OptResult result = userManager.signIn(userName, password, remember);

		if (result.getCode() == 0) {
			SavedRequest savedReq = WebUtils.getSavedRequest(request);
			System.out.println(savedReq.getRequestUrl());
			if (savedReq == null || savedReq.getRequestUrl() == null || savedReq.getRequestUrl().indexOf("favicon.ico")>-1) {
				result.setResult(request.getContextPath());
			} else {
				// 返回上次请求的地址
				result.setResult(savedReq.getRequestUrl());
			}
		}
		return result;

	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) {

		userManager.signOut();

		// 跳转到登录页面
		return "redirect:login";
	}

}
