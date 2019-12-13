package com.jkk.leave.service.impl;

import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.POJO.TeacherLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.mapper.LessonMapper;
import com.jkk.leave.mapper.TeacherLeaveListMapper;
import com.jkk.leave.service.LessonService;
import com.jkk.leave.service.StudentInfoService;
import com.jkk.leave.service.TeacherApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherApplyServiceImpl implements TeacherApplyService {
	private final TeacherLeaveListMapper teacherLeaveListMapper;
	private final LessonService lessonService;
	private final StudentInfoService studentInfoService;
	public TeacherApplyServiceImpl(LessonService lessonService, TeacherLeaveListMapper teacherLeaveListMapper, StudentInfoService studentInfoService) {
		this.lessonService = lessonService;
		this.teacherLeaveListMapper = teacherLeaveListMapper;
		this.studentInfoService = studentInfoService;
	}

	@Override
	public List<Lesson> getLessons(User teacher, String team) {
		return lessonService.getTeacherLessonInTeam(teacher.getId(), team);
	}

	@Override
	@Transactional
	public List<TeacherLeaveList> getApplyList(User teacher, Integer year, Integer week, String lessonId) {
		List<TeacherLeaveList> teacherLeaveLists = teacherLeaveListMapper.selectCustom(week,year,lessonId,teacher.getId());

		for (TeacherLeaveList teacherLeaveList : teacherLeaveLists) {
			teacherLeaveList.setClasses(studentInfoService.getStudentClass(teacherLeaveList.getStudentId()));
			teacherLeaveList.setCounselorName(studentInfoService.getStudentCounselorName(teacherLeaveList.getStudentId()));

			if (!teacherLeaveList.getLooked()){
				TeacherLeaveListBaseDO teacherLeaveListBaseDO =
						TeacherLeaveListBaseDO.builder()
						.id(teacherLeaveList.getId())
						.looked(true)
						.build();
				teacherLeaveListMapper.updateByPrimaryKeySelective(teacherLeaveListBaseDO);
			}
		}

		return teacherLeaveLists;
	}

	@Override
	public int addApply(TeacherLeaveListBaseDO teacherLeaveListBaseDO) {
		return teacherLeaveListMapper.insertSelective(teacherLeaveListBaseDO);
	}

}
