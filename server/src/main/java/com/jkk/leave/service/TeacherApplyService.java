package com.jkk.leave.service;

import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.POJO.TeacherLeaveList;
import com.jkk.leave.entity.POJO.User;

import java.util.List;

public interface TeacherApplyService {
	List<Lesson> getLessons(User teacher, String team);

	List<TeacherLeaveList> getApplyList(User teacher, Integer year, Integer week, String lessonId);

	int addApply(TeacherLeaveListBaseDO teacherLeaveListBaseDO);
}
