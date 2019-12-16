package com.jkk.leave.service.impl;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.mapper.UserMapper;
import com.jkk.leave.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public User isLogin(User user) {
		return userMapper.isLogin(user);
	}

	@Override
	public int getTypeIdById(Integer id) {
		return userMapper.selectByPrimaryKey(id).getType();
	}

	@Override
	public String getUserName(Integer id) {
		return userMapper.selectByPrimaryKey(id).getName();
	}

	public User getUserById(Integer id){
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int modifyPwd(Integer id, String pwd) {
		User user = new User();
		user.setId(id);
		user.setPassword(pwd);
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int modifyAvatar(Integer id, String avatarPath) {
		User user = new User();
		user.setId(id);
		user.setAvatar(avatarPath);
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int modifyEMail(Integer id, String eMail) {
		User user = new User();
		user.setId(id);
		user.setEMail(eMail);
		return userMapper.updateByPrimaryKeySelective(user);
	}


}
