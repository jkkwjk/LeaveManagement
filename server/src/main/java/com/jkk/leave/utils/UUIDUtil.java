package com.jkk.leave.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
