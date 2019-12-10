package com.jkk.leave.utils;

import java.util.Date;

public final class TimeUtil {
	private static boolean time1MoreTime2(Long time1, Long time2){
		return time1 > time2;
	}

	private static boolean time1MoreTime2(Date time1, Date time2){
		return time1MoreTime2(time1.getTime(), time2.getTime());
	}

	public static boolean effectiveTime(Long startTime, Long endTime){
		return time1MoreTime2(endTime, new Date().getTime()) && time1MoreTime2(endTime, startTime);
	}
}
