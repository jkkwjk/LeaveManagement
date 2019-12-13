package com.jkk.leave.service.impl;

import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.mapper.LessonMapper;
import com.jkk.leave.service.LessonService;
import com.jkk.leave.tools.TimeTool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
	private final LessonMapper lessonMapper;

	public LessonServiceImpl(LessonMapper lessonMapper) {
		this.lessonMapper = lessonMapper;
	}

	@Override
	public List<Lesson> getTeacherLesson(Integer teacherId) {
		return this.getTeacherLessonInTeam(teacherId, TimeTool.getThisTeam());
	}

	@Override
	public List<Lesson> getTeacherLessonInTeam(Integer teacherId, String team) {
		return lessonMapper.getTeacherAllLesson(teacherId, team);
	}

	@Override
	public List<Lesson> getStudentLesson(Integer studentId) {
		return this.getStudentLessonInTeam(studentId, TimeTool.getThisTeam());
	}

	@Override
	public List<Lesson> getStudentLessonInTeam(Integer studentId, String team) {
		return lessonMapper.getStudentAllLesson(studentId, team);
	}
}
