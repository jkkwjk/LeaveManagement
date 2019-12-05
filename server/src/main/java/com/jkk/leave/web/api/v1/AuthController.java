package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.VO.AuthVO;
import com.jkk.leave.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping()
	public AuthVO getAuth(@SessionAttribute("user") User user){
		return authService.getAuthById(user.getType());
	}
}
