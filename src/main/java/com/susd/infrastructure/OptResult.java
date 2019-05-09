package com.susd.infrastructure;

/**
 * 业务逻辑操作统一返回结果
 * @author TY
 *
 */
public class OptResult {
	public OptResult(int errcode,Object result) {
		this.errcode=errcode;
		this.result=result;
	}
	
	//结果编码
	private int errcode;
	
	//结果内容
	private Object result;

	/**
	 * 获取结果编码
	 * @return 返回结果编码
	 */
	public int getCode() {
		return this.errcode;
	}
	
	/**
	 * 设置结果编码
	 * @param errcode 错误编码
	 */
	public void setCode(int errcode) {
		this.errcode=errcode;
	}
	
	/**
	 * 获取结果内容
	 * @return 返回结果内容
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * 设置结果内容
	 * @param result 结果内容
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	
	/**
	 * 请求成功
	 * @param msg 成功消息
	 * @return 成功结果
	 */
	public static OptResult Successed(Object msg) {
		OptResult res= new OptResult(0,msg);		
		return res;
	}
	
	/**
	 * 请求成功
	 * @return 成功结果
	 */
	public static OptResult Successed() {
		OptResult res= new OptResult(0,"ok");
		return res;
	}
	
	/**
	 * 请求失败
	 * @param errcode 错误代码
	 * @param errmsg 错误消息
	 * @return 返回错误结果
	 */
	public static OptResult Failed(int errcode,Object errmsg) {
		OptResult res= new OptResult(errcode,errmsg);
		return res;
	}
	
	/**
	 * 请求失败
	 * @param errcode 错误代码
	 * @return 返回错误结果
	 */
	public static OptResult Failed(Object errmsg) {
		OptResult res= new OptResult(50001,errmsg);
		return res;
	}
}
