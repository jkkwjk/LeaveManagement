package com.jkk.leave.exception;

import com.jkk.leave.utils.RestfulRes;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public Object defaultExceptionHandler(HttpServletRequest request, Exception e) throws Exception{
		if (e instanceof DuplicateKeyException){
			return RestfulRes.fail("请不要重复点击!");
		}
		throw e;
	}
}
