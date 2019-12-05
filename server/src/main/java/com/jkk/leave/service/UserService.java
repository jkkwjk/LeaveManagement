package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;

import javax.validation.constraints.NotNull;

public interface UserService {
	boolean isLogin(User user);

	int getTypeIdById(Integer id);

	String getUserName(Integer id);
}
