package com.jkk.leave.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

import java.util.Date;

public final class TimeTool {
	private static boolean time1MoreTime2(Long time1, Long time2){
		return time1 > time2;
	}

	private static boolean time1MoreTime2(Date time1, Date time2){
		return time1MoreTime2(time1.getTime(), time2.getTime());
	}

	public static boolean effectiveTime(Long startTime, Long endTime){
		return time1MoreTime2(endTime, new Date().getTime()) && time1MoreTime2(endTime, startTime);
	}

	public static String getThisTeam(){
		return HttpUtil.get("http://101.132.168.131:33033"); //fix 获得日期
	}
}
