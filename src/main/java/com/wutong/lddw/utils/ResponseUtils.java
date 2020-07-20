package com.wutong.lddw.utils;

/**
 * @Author wangzhichao-2
 * @Date 2020/2/6 13:28
 **/
public class ResponseUtils {
	private ResponseUtils(){}
	
	public static Response success() {
		return result(true, null, null);
	}
	
	public static Response success(String message) {
		return result(true, message, null);
	}

	public static Response success(Object data) {
		return result(true, "", data);
	}

	public static Response success(String message, Object data) {
		return result(true, message, data);
	}

	public static Response fail() {
		return result(false, null, null);
	}
	
	public static Response fail(String message) {
		return result(false, message, null);
	}
	
	public static Response fail(String message, Object data) {
		return result(false, message, data);
	}
	
	public static Response result(boolean success, String message, Object data) {
		return new Response(success, message, data);
	}
}
