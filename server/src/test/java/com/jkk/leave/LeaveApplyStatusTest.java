package com.jkk.leave;

import com.alibaba.fastjson.JSON;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.mapper.CounselorLeaveListMapper;
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
import java.util.List;

@SpringBootTest
public class LeaveApplyStatusTest {

	@Autowired
	private CounselorLeaveListMapper counselorLeaveListMapper;
	@Test
	public void getApplyList() throws Exception {
		List<ManageLeaveList> ret = counselorLeaveListMapper.selectArchive(2,1575179503000L,1577771503000L);

		int a = 1;

	}

}
