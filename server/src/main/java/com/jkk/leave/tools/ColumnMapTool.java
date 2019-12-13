package com.jkk.leave.tools;

import java.util.HashMap;
import java.util.Map;

public final class ColumnMapTool {
	public static Map<String,String> map = new HashMap<>();
	static {
		map.put("sendTime", "send_time");
		map.put("startTime", "start_time");
		map.put("endTime", "end_time");
		map.put("type", "type");
		map.put("detail", "detail");
		map.put("team", "team");
		map.put("number", "stu_id");
		map.put("name", "name");
		map.put("showWhat", "status");
	}
}
