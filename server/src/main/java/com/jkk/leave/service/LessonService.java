package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.Lesson;

import java.util.List;

public interface LessonService {
	List<Lesson> getTeacherLesson(Integer teacherId);

	List<Lesson> getTeacherLessonInTeam(Integer teacherId, String team);

	List<Lesson> getStudentLesson(Integer studentId);

	List<Lesson> getStudentLessonInTeam(Integer studentId, String team);
}
