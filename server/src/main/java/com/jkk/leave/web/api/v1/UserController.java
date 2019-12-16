package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.service.UserService;
import com.jkk.leave.tools.DataTool;
import com.jkk.leave.utils.RestfulRes;
import com.jkk.leave.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

	@GetMapping("logout")
	public RestfulRes logout(HttpSession session){
		session.invalidate();
		return RestfulRes.success();
	}

	@PostMapping("getInfo")
	public RestfulRes<User> getInfo(@SessionAttribute("user")User user){
		return RestfulRes.success(userService.getUserById(user.getId()));
	}

	@PostMapping("modifyPwd")
	public RestfulRes modifyPwd(String pwd, @SessionAttribute("user")User user){
		if (DataTool.effectivePwd(pwd) && userService.modifyPwd(user.getId(), pwd) == 1){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("修改密码失败");
		}
	}

	@PostMapping("modifyEMail")
	public RestfulRes modifyEMail(String email, @SessionAttribute("user")User user){
		if (DataTool.effectiveEmail(email) && userService.modifyEMail(user.getId(), email) == 1){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("修改邮箱失败");
		}
	}

	@Value("${prop.img}")
	private String filePath;
	@PostMapping("modifyAvatar")
	public RestfulRes modifyAvatar(@RequestParam("file") MultipartFile file, @SessionAttribute("user")User user){
		if (file.isEmpty()) {
			return RestfulRes.fail("文件不能为空");
		}
		String fileName = UUIDUtil.getUUID();

		File dest = new File(filePath + fileName);
		try {
			file.transferTo(dest);
			if (userService.modifyAvatar(user.getId(), fileName) == 1){
				return RestfulRes.success(fileName);
			}else {
				return RestfulRes.fail("上传文件失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return RestfulRes.fail("上传文件失败");
		}
	}
}
