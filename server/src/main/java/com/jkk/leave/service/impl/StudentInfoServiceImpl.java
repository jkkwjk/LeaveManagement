package com.jkk.leave.service.impl;

import com.jkk.leave.mapper.StudentInfoMapper;
import com.jkk.leave.service.StudentInfoService;
import com.jkk.leave.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class StudentInfoServiceImpl implements StudentInfoService {
	private final StudentInfoMapper studentInfoMapper;
	private final UserService userService;

	public StudentInfoServiceImpl(StudentInfoMapper studentInfoMapper, UserService userService) {
		this.studentInfoMapper = studentInfoMapper;
		this.userService = userService;
	}

	@Override
	public Integer getStudentClass(Integer studentId) {
		return studentInfoMapper.getClass(studentId);
	}

	@Override
	public String getStudentCounselorName(Integer studentId) {
		return userService.getUserName(studentInfoMapper.getCounselorId(studentId));
	}

	@Override
	public Integer getStudentCounselorId(Integer studentId) {
		return studentInfoMapper.getCounselorId(studentId);
	}
}
