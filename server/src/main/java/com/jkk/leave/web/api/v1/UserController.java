package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.service.UserService;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("login")
	public RestfulRes login(String id, String password, HttpSession session){
		User user = new User();


		try {
			user.setId(Integer.parseInt(id));
		}catch (NumberFormatException e){
			return RestfulRes.fail("账号只能是数字");
		}

		user.setPassword(password);
		User res = userService.isLogin(user);
		if (res != null){
			session.setAttribute("user",res);
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("账号或密码错误");
		}

	}
}
