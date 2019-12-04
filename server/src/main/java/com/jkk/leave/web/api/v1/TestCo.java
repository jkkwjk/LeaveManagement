package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.Card;
import com.jkk.leave.entity.POJO.Test;
import com.jkk.leave.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestCo {
	private final TestService testService;

	public TestCo(TestService testService) {
		this.testService = testService;
	}

	@RequestMapping("hello")
	public Test hello(){

		return testService.getStr();
	}
	@RequestMapping("shu")
	public Card shu(int id){
		return testService.selectById(id);
	}
}
