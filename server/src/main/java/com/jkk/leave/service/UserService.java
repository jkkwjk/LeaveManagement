package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;

import javax.validation.constraints.NotNull;

public interface UserService {
	User isLogin(User user);

	int getTypeIdById(Integer id);

	String getUserName(Integer id);

	User getUserById(Integer id);

	int modifyPwd(Integer id, String pwd);

	int modifyAvatar(Integer id, String avatarPath);

	int modifyEMail(Integer id, String eMail);
}
