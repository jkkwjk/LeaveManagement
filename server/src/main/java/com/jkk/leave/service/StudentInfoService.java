package com.jkk.leave.service;

public interface StudentInfoService {
	Integer getStudentClass(Integer studentId);

	String getStudentCounselorName(Integer studentId);

	Integer getStudentCounselorId(Integer studentId);
}
