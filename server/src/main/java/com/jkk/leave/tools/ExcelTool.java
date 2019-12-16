package com.jkk.leave.tools;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class ExcelTool {
	public static void writeToFile(String fileName, String filePath, Map<String, String> headerAlias, Iterable<?> beans){
		ExcelWriter writer = ExcelUtil.getWriter(filePath + File.separator + fileName);
		writer.setHeaderAlias(headerAlias);
		CellStyle style = writer.getStyleSet().getHeadCellStyle();
		Font font = writer.createFont();
		font.setBold(true);
		font.setFontHeight((short) 300);
		style.setFont(font);

		writer.setColumnWidth(-1,19);
		writer.write(beans, true);

		writer.close();
	}

	public static void downLoad(HttpServletResponse response, String fileName, Map<String, String> headerAlias, Iterable<?> beans) throws UnsupportedEncodingException {
		ExcelWriter writer = ExcelUtil.getWriter();
		writer.setHeaderAlias(headerAlias);
		CellStyle style = writer.getStyleSet().getHeadCellStyle();
		Font font = writer.createFont();
		font.setBold(true);
		font.setFontHeight((short) 300);
		style.setFont(font);

		writer.setColumnWidth(-1,19);
		writer.write(beans, true);

		String nameDisplayEncode = new String(fileName.getBytes("GB2312"),"ISO-8859-1");

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition","attachment;filename="+ nameDisplayEncode +".xls");

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			writer.flush(out, true);
			writer.close();

		}catch (Exception e){
			if (out != null)
				IoUtil.close(out);
		}
		writer.close();
	}

	public static void downLoad(HttpServletResponse response, String fileName, Iterable<?> beans) throws UnsupportedEncodingException{
		Map<String, String> headerAlias = new ConcurrentHashMap<>();
		headerAlias.put("sendTime", "发送时间");
		headerAlias.put("studentId", "学号");
		headerAlias.put("studentName", "学生姓名");
		headerAlias.put("classes", "学生班级");
		headerAlias.put("counselor", "辅导员姓名");
		headerAlias.put("type", "请假类型");
		headerAlias.put("detail", "请假原因");
		headerAlias.put("startTime", "开始时间");
		headerAlias.put("endTime", "结束时间");
		headerAlias.put("showWhat","请假结果");

		ExcelTool.downLoad(response, fileName, headerAlias, beans);
	}
}
