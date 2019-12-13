package com.jkk.leave.tools;

public final class DataTool {
	public static Boolean effectiveType(String type){
		return type.equals("事假") || type.equals("病假") ||type.equals("公假");
	}
}
