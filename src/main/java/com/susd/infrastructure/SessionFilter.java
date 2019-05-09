package com.susd.infrastructure;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;

public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String uri = httpServletRequest.getRequestURI();
		boolean b = uri.indexOf("ajaxLogin") >= 0; // ajax 登陆 排除
		if (!SecurityUtils.getSubject().isAuthenticated() && !b) {
			// 判断session里是否有用户信息
			if (httpServletRequest.getHeader("x-requested-with") != null
					&& httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				// 如果是ajax请求响应头会有，x-requested-with
				// httpServletResponse.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
				redirect(httpServletRequest, httpServletResponse);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	void redirect(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
		response.setContentType("text/html");
		PrintWriter out;
		try {
			
			out = response.getWriter();
			out.print("<script>window.location = \""+request.getContextPath()+"/login\";</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
