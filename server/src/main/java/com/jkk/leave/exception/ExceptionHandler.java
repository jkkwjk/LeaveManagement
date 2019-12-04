package com.jkk.leave.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public Map defaultExceptionHandler(HttpServletRequest request, Exception e) throws Exception{
		Map<String, String> map = new HashMap<>();
		//e.printStackTrace();
		map.put("error", "i am handler");
		return map;
	}
}
