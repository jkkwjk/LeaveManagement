package com.jkk.leave.tools;

import java.util.regex.Pattern;

public final class DataTool {
	public static Boolean effectiveType(String type){
		return type.equals("事假") || type.equals("病假") ||type.equals("公假");
	}

	public static Boolean effectivePwd(String pwd){
		Pattern pattern = Pattern.compile("^(?!\\d{6,8}$)(?! )(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9_]{6,16}$");
		return pattern.matcher(pwd).matches();
	}

	public static Boolean effectiveEmail(String email){
		Pattern pattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
		return pattern.matcher(email).matches();
	}
}
