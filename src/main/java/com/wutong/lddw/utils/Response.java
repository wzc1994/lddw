package com.wutong.lddw.utils;

/**
 * 返回结果
 *
 * @Author wangzhichao
 * @Date 2020/2/6 13:28
 **/
public class Response {

	private boolean success;
	private String message;
	private Object data;
	
	public Response(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
