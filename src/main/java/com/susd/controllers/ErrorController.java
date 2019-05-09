package com.susd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/error")
@Controller
public class ErrorController {

	/**
	 * 未授权页面
	 * @return
	 */
	@RequestMapping(value="/unauthorized")
	public String unauthorized() {
		
		return "error/unauthorized";
	}
}
