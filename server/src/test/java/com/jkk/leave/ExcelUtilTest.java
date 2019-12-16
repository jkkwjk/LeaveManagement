package com.jkk.leave;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class ExcelUtilTest {
	@Autowired
	private MockMvc mockMvc;
	private MockHttpSession session;

	@Test
	public void exportExcel(){

		List<String> title = CollUtil.newArrayList("发送时间", "学号", "学生姓名", "学生班级", "辅导员姓名", "请假类型", "请假原因", "开始时间", "结束时间", "请假结果");
		List<String> row2 = CollUtil.newArrayList("发送时间", "学号", "学生姓名", "学生班级", "辅导员姓名", "请假类型", "请假原因", "开始时间", "结束时间", "请假结果");
		List<String> row3 = CollUtil.newArrayList("发送时间", "学号", "学生姓名", "学生班级", "辅导员姓名", "请假类型", "请假原因", "开始时间", "结束时间", "请假结果");
		List<String> row4 = CollUtil.newArrayList("发送时间", "学号", "学生姓名", "学生班级", "辅导员姓名", "请假类型", "请假原因", "开始时间", "结束时间", "请假结果");
		List<String> row5 = CollUtil.newArrayList("发送时间", "学号", "学生姓名", "学生班级", "辅导员姓名", "请假类型", "请假原因", "开始时间", "结束时间", "请假结果");

		List<List<String>> rows = CollUtil.newArrayList(row2, row3, row4, row5);
		ExcelWriter writer = ExcelUtil.getWriter("F:/webUpload/"+ UUID.randomUUID().toString() +".xlsx");
		writer.addHeaderAlias("sendTime", "发送时间");
		writer.addHeaderAlias("studentId", "学号");
		writer.addHeaderAlias("studentName", "学生姓名");
		writer.addHeaderAlias("classes", "学生班级");
		writer.addHeaderAlias("counselor", "辅导员姓名");
		writer.addHeaderAlias("type", "请假类型");
		writer.addHeaderAlias("detail", "请假原因");
		writer.addHeaderAlias("startTime", "开始时间");
		writer.addHeaderAlias("endTime", "结束时间");
		writer.addHeaderAlias("endTime", "结束时间");
		writer.addHeaderAlias("showWhat","请假结果");

		writer.merge(title.size() - 1, "2019-11-23 00:00:00 到 2019-11-23 00:00:00 的辅导员请假归档");
		CellStyle style = writer.getStyleSet().getHeadCellStyle();
		Font font = writer.createFont();
		font.setBold(true);
		font.setFontHeight((short) 500);
		style.setFont(font);

		writer.autoSizeColumnAll();
		writer.setColumnWidth(-1,19);
		writer.writeRow(title, false);
		writer.write(rows, false);

		writer.close();
	}

	@Test
	public void exportExcelHttp() throws Exception{
		session = new MockHttpSession();
		RequestBuilder login = MockMvcRequestBuilders.get("/user/login")
				.param("id","1")
				.param("password","1")
				.session(session);
		RequestBuilder getAuth = MockMvcRequestBuilders.get("/archive")
				.session(session);
		mockMvc.perform(login);

		System.out.println(mockMvc.perform(getAuth).andReturn().getResponse().getContentAsString());
	}
}
