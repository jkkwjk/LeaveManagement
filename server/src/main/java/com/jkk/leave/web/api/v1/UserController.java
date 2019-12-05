package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.service.UserService;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("login")
	public RestfulRes login(String id, String password){
		User user = new User();
		try {
			user.setId(Integer.parseInt(id));
		}catch ()
//		if (userService.isLogin(user)){
//			return RestfulRes.success();
//		}else {
//			return RestfulRes.fail("账号或密码错误");
//		}

	}
}
