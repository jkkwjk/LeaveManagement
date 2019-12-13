package com.jkk.leave;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.service.LeaveApplyService;
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
public class LeaveApplyStatusTest {

	@Autowired
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Autowired
	private LeaveApplyService leaveApplyService;

	@Test
	public void getApplyList() throws Exception {
//		session = new MockHttpSession();
//		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
//				.param("id", "1")
//				.param("password", "1")
//				.session(session);
//		RequestBuilder getAuth = MockMvcRequestBuilders.post("/stu")
//				.param("page","1")
//				.param("num","20")
//				.param("custom","{\"sort\":{\"prop\":\"start_time\",\"order\":\"1\"},\"custom\":[{\"prop\":\"type\",\"content\":\"公假\"},{\"prop\":\"type\",\"content\":\"病假\"}]}")
//				.session(session);
//		mockMvc.perform(login);
//
//		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());
		User user = new User();
		user.setId(1);
		leaveApplyService.getLeaveStatus(28,user);

	}

}
