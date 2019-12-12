package com.jkk.leave.service.impl;

import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.POJO.TeacherLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.mapper.LessonMapper;
import com.jkk.leave.mapper.StudentInfoMapper;
import com.jkk.leave.mapper.TeacherLeaveListMapper;
import com.jkk.leave.mapper.UserMapper;
import com.jkk.leave.service.TeacherApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherApplyServiceImpl implements TeacherApplyService {
	private final LessonMapper lessonMapper;
	private final TeacherLeaveListMapper teacherLeaveListMapper;
	private final StudentInfoMapper studentInfoMapper;
	private final UserMapper userMapper;
	public TeacherApplyServiceImpl(LessonMapper lessonMapper, TeacherLeaveListMapper teacherLeaveListMapper, StudentInfoMapper studentInfoMapper, UserMapper userMapper) {
		this.lessonMapper = lessonMapper;
		this.teacherLeaveListMapper = teacherLeaveListMapper;
		this.studentInfoMapper = studentInfoMapper;
		this.userMapper = userMapper;
	}

	@Override
	public List<Lesson> getLessons(User teacher, String team) {
		return lessonMapper.getTeacherAllLesson(teacher.getId(), team);
	}

	@Override
	@Transactional
	public List<TeacherLeaveList> getApplyList(User teacher, Integer year, Integer week, String lessonId) {
		List<TeacherLeaveList> teacherLeaveLists = teacherLeaveListMapper.selectCustom(week,year,lessonId,teacher.getId());

		for (TeacherLeaveList teacherLeaveList : teacherLeaveLists) {
			teacherLeaveList.setClasses(studentInfoMapper.getClass(teacherLeaveList.getStudentId()));
			teacherLeaveList.setCounselorName(userMapper.getUserInfoById(studentInfoMapper.getCounselorId(teacherLeaveList.getStudentId())).getName());

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

}
