package com.jkk.leave;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jkk.leave.tools.ApplyStatus;
import com.jkk.leave.tools.TimeTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

public class TimeTest {

	@Test
	synchronized public void getTime() throws Exception {
		Date startTime = new Date(1575343980000L); //12.3
		Date endTime = new Date(1576080000000L); //12.12

		Date lessonEndTime = new Date(1576080000000L);

		//获得下一天
		DateTime nextDay = DateUtil.beginOfDay(DateUtil.offsetDay(startTime, 1));
		DateTime lastDay = DateUtil.endOfDay(DateUtil.offsetDay(endTime, -1));
		String team = "2019-2020-1"; // 请假不可能跨学期
		for (long time = nextDay.getTime(); time <= lastDay.getTime(); time = DateUtil.offsetDay(new Date(time),1).getTime()){

			System.out.println(DateUtil.format(new Date(time), "YYYY-MM-dd HH:mm:ss"));
			//System.out.print(TimeTool.getWeekOfTeam(team, time)+" ");
			int week = TimeTool.getChineseWeek(time);
			//System.out.println(week);
		}

		//System.out.println(DateUtil.format(endTime, "YYYY-MM-dd HH:mm:ss"));
	}
}
