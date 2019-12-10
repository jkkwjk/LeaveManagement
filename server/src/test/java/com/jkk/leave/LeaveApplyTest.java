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

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class LeaveApplyTest {

	@Autowired
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Test
	public void getApplyList() throws Exception {
		session = new MockHttpSession();
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("id", "1")
				.param("password", "1")
				.session(session);
		RequestBuilder getAuth = MockMvcRequestBuilders.post("/stu")
				.param("page","1")
				.param("num","20")
				.param("custom","{\"sort\":{\"prop\":\"start_time\",\"order\":\"1\"},\"custom\":[{\"prop\":\"type\",\"content\":\"公假\"},{\"prop\":\"type\",\"content\":\"病假\"}]}")
				.session(session);
		mockMvc.perform(login);

		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());

	}
	@Test
	public void addApplyList() throws Exception {
		session = new MockHttpSession();
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("id", "1")
				.param("password", "1")
				.session(session);
		RequestBuilder getAuth = MockMvcRequestBuilders.post("/stu/add")
				.param("detail","分页")
				.param("type","病假")
				.param("startTime", String.valueOf(new Date().getTime()))
				.param("endTime", String.valueOf(new Date().getTime()+999999))
				.session(session);
		mockMvc.perform(login);
		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());
	}
	@Test
	public void modifyApplyList() throws Exception {
		session = new MockHttpSession();
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("id", "1")
				.param("password", "1")
				.session(session);
		RequestBuilder getAuth = MockMvcRequestBuilders.post("/stu/modify")
				.param("id","2")
				.param("detail","分页")
				.param("type","病假")
				.param("startTime", String.valueOf(new Date().getTime()))
				.param("endTime", String.valueOf(new Date().getTime()+999999))
				.session(session);
		mockMvc.perform(login);
		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());
	}
}
