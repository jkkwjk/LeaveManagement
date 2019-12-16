package com.jkk.leave;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class LeaveApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Test
	public void getAuthTest() throws Exception{
		session = new MockHttpSession();
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("id","1")
				.param("password","1")
				.session(session);
		RequestBuilder getAuth = MockMvcRequestBuilders.get("/stu/getArchive")
				.param("startTime","1575129600000")
				.param("endTime","1577721600000")
				.session(session);
		mockMvc.perform(login);

		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());

	}

	@Test
	public void selectAccountItem() throws Exception{
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("stuNo","")
				.param("stuPwd","")
				.session(session);
		RequestBuilder addAccount = MockMvcRequestBuilders.get("/account/getRecord")
				.param("start","0")
				.param("len","10")
				.session(session);
		mockMvc.perform(login);

		System.out.println(mockMvc.perform(addAccount).andReturn().getResponse().getContentAsString());

	}

}
