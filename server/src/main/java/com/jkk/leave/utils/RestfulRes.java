package com.jkk.leave.utils;

import lombok.Data;
@Data
@SuppressWarnings("unchecked")
public class RestfulRes<T> {
	private int code;
	private String msg;
	private T data;

	private RestfulRes(){
		this.code = 200;
		this.msg = "success";
	}

	private RestfulRes(String msg) {
		this.code = 505;
		this.msg = msg;
	}
	public static <T> RestfulRes<T> success(T data){
		RestfulRes restfulRes = new RestfulRes<>();
		restfulRes.setData(data);
		return restfulRes;
	}
	public static <T> RestfulRes<T> success(){
		return new RestfulRes<>();
	}
	public static <T> RestfulRes<T> fail(String msg){
		return new RestfulRes<T>(msg);
	}
	public static <T> RestfulRes<T> fail(){
		RestfulRes restfulRes = new RestfulRes<T>();
		restfulRes.setCode(505);
		return restfulRes;
	}
}
