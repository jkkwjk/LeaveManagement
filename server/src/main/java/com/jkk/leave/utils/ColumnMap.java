package com.jkk.leave.utils;

import java.util.HashMap;
import java.util.Map;

public final class ColumnMap {
	public static Map<String,String> map = new HashMap<>();
	static {
		map.put("sendTime", "send_time");
		map.put("startTime", "start_time");
		map.put("endTime", "end_time");
		map.put("type", "type");
		map.put("detail", "detail");
		map.put("showWhat", "status");
	}
}
