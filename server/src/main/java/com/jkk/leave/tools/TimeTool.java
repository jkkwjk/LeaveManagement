package com.jkk.leave.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	public static List<Long[]> getOffsetTime(Date time){
		List<Long[]> ret = new ArrayList<>();
		ret.add(new Long[]{DateUtil.beginOfDay(time).getTime(),DateUtil.endOfDay(time).getTime()}); // time当天

		Date yesterday = DateUtil.offsetDay(time,-1);
		ret.add(new Long[]{DateUtil.beginOfDay(yesterday).getTime(),DateUtil.endOfDay(yesterday).getTime()}); // time昨天

		Date weekBegin = DateUtil.beginOfWeek(time);
		Date weekEnd = DateUtil.endOfWeek(time);
		ret.add(new Long[]{DateUtil.beginOfDay(weekBegin).getTime(),DateUtil.endOfDay(weekEnd).getTime()}); // time当周

		Date tmp = DateUtil.offsetWeek(time,-1);
		weekBegin = DateUtil.beginOfWeek(tmp);
		weekEnd = DateUtil.endOfWeek(tmp);
		ret.add(new Long[]{DateUtil.beginOfDay(weekBegin).getTime(),DateUtil.endOfDay(weekEnd).getTime()}); // time上周

		return ret;
	}

	public static Long[] getThisMonth(){
		Date date = new Date();
		Long[] ret = new Long[2];
		ret[0] = DateUtil.beginOfMonth(date).getTime();
		ret[1] = DateUtil.endOfDay(date).getTime();
		return ret;
	}

	public static Long[] getThisYear(){
		Date date = new Date();
		Long[] ret = new Long[2];
		ret[0] = DateUtil.beginOfYear(date).getTime();
		ret[1] = DateUtil.endOfYear(date).getTime();
		return ret;
	}
}
