package com.susd.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.susd.infrastructure.OptResult;

/**
 * 全局异常处理
 * 
 * @author susd
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAspect {
	private static final Logger log = Logger.getLogger(ExceptionAspect.class);

	/**
	 * 400 - Bad Request
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public OptResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		log.error("json字符串格式错误", e);

		return OptResult.Failed(50400, "json字符串格式错误");
	}

	/**
	 * 400 - Bad Request
	 * 
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public OptResult handleValidationException(MethodArgumentNotValidException e) {
		log.error("参数解析失败", e);

		return OptResult.Failed(50400, "参数解析失败");
	}
	
	/**
	 * 参数验证错误
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public OptResult handleBindException(BindException e) {
		log.error("参数验证错误", e);
		return OptResult.Failed(50400, e.getAllErrors().get(0).getDefaultMessage());
	}
	
	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public OptResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法", e);

		return OptResult.Failed(50405, "不支持当前请求方法");
	}

	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public OptResult handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("不支持当前媒体类型", e);
		return OptResult.Failed(50415, "不支持当前媒体类型");
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public OptResult handleException(Exception e) {
		log.error("内部服务出错", e);
		return OptResult.Failed(50500, "内部服务出错");
	}
}
