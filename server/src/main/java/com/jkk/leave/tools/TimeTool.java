package com.jkk.leave.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

import java.util.Calendar;
import java.util.Date;

public final class TimeTool {
	private static boolean time1MoreTime2(Long time1, Long time2){
		return time1 > time2;
	}

	private static boolean time1MoreTime2(Date time1, Date time2){
		return time1MoreTime2(time1.getTime(), time2.getTime());
	}

	public static boolean effectiveTime(Long startTime, Long endTime){
		return time1MoreTime2(endTime, new Date().getTime()) && time1MoreTime2(endTime, startTime) &&
				!DateUtil.isSameDay(new Date(startTime), new Date(endTime));
	}

	public static String getThisTeam(){
		return HttpUtil.get("http://101.132.168.131:33033"); //fix 获得日期
	}

	public static int getChineseWeek(Date time){
		return DateUtil.dayOfWeek(time) ==1?7:DateUtil.dayOfWeek(time)-1;
	}

	public static int getChineseWeek(Long time){
		return getChineseWeek(new Date(time));
	}

	synchronized public static int getWeekOfYear(String team, Long time){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(team.split("-")[0]),Calendar.JANUARY,1);
		return DateUtil.weekCount(calendar.getTime(),new Date(time));
	}

	public static Boolean isInTime(Long startTime, Long endTime){
		return false;
	}
}
