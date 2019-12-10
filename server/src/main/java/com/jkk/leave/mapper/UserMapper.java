package com.jkk.leave.mapper;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends MyBatisBaseDao<User, Integer> {
	User isLogin(User user);

	User getUserInfoById(Integer userId);
}