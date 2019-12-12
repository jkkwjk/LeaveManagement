package com.jkk.leave.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.*;
import com.jkk.leave.service.CollegeApplyService;
import com.jkk.leave.tools.TimeTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CollegeApplyServiceImpl implements CollegeApplyService {
	private final CollegeLeaveListMapper collegeLeaveListMapper;
	private final UserMapper userMapper;
	private final LeaveApplyMapper leaveApplyMapper;
	private final TeacherLeaveListMapper teacherLeaveListMapper;
	private final StudentInfoMapper studentInfoMapper;
	private final LessonMapper lessonMapper;
	public CollegeApplyServiceImpl(CollegeLeaveListMapper collegeLeaveListMapper, UserMapper userMapper, LeaveApplyMapper leaveApplyMapper, TeacherLeaveListMapper teacherLeaveListMapper, StudentInfoMapper studentInfoMapper, LessonMapper lessonMapper) {
		this.collegeLeaveListMapper = collegeLeaveListMapper;
		this.userMapper = userMapper;
		this.leaveApplyMapper = leaveApplyMapper;
		this.teacherLeaveListMapper = teacherLeaveListMapper;
		this.studentInfoMapper = studentInfoMapper;
		this.lessonMapper = lessonMapper;
	}


	@Override
	@Transactional
	public List<ManageLeaveList> getApply(int page, int num, Filter filter, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<ManageLeaveList> manageLeaveLists = collegeLeaveListMapper.selectCustom(filter, sorter);

		for (ManageLeaveList manageLeaveList : manageLeaveLists) {
			if (manageLeaveList.getAllow() == null){
				manageLeaveList.setShowWhat("button");
			}else if (manageLeaveList.getAllow()){
				manageLeaveList.setShowWhat("allow");
			}else {
				manageLeaveList.setShowWhat("reject");
			}

			manageLeaveList.setStudentName(userMapper.getUserInfoById(manageLeaveList.getStudentId()).getName());
			manageLeaveList.setClasses(studentInfoMapper.getClass(manageLeaveList.getStudentId()));
			manageLeaveList.setCounselorName(userMapper.getUserInfoById(studentInfoMapper.getCounselorId(manageLeaveList.getStudentId())).getName());
			// 已查看
			if (!manageLeaveList.getLooked()){
				ManageLeaveListBaseDO manageLeaveListBaseDO =
						ManageLeaveListBaseDO.builder()
						.id(manageLeaveList.getId())
						.looked(true)
						.build();
				collegeLeaveListMapper.updateByPrimaryKeySelective(manageLeaveListBaseDO);
			}
		}
		return manageLeaveLists;
	}

	@Override
	@Transactional
	public int allowApply(ManageLeaveList manageLeaveList) {
		int num = 0;
		Integer leaveId = manageLeaveList.getId();
		ManageLeaveListBaseDO manageLeaveListBaseDO =
				ManageLeaveListBaseDO.builder()
						.id(leaveId)
						.allow(true)
						.build();

		if (collegeLeaveListMapper.updateByPrimaryKeySelective(manageLeaveListBaseDO) == 1){
			//设置主申请表 状态为同意
			LeaveApplyDO updateLeaveApply =
					LeaveApplyDO.builder()
					.id(leaveId)
					.status(2)
					.build();
			if (leaveApplyMapper.updateByPrimaryKeySelective(updateLeaveApply) == 1){
				// 添加到课程老师中
				num = 1;
				String team = TimeTool.getThisTeam();
				LeaveApplyDO selectLeaveApply = leaveApplyMapper.selectByPrimaryKey(leaveId);
				List<Lesson> allLesson = lessonMapper.getStudentAllLesson(selectLeaveApply.getStudentId(), team);
				ConcurrentHashMap<Integer, List<Lesson>> lessonMap = new ConcurrentHashMap<>();
				for (int i=1; i<=7; ++i)
					lessonMap.put(i,new ArrayList<>());
				for (Lesson lesson : allLesson) {
					lessonMap.get(lesson.getWeek()).add(lesson);
				}

				List<Lesson> lessons;
				Date startTime = new Date(selectLeaveApply.getStartTime());
				Date endTime = new Date(selectLeaveApply.getEndTime());
				DateTime nextDay = DateUtil.beginOfDay(DateUtil.offsetDay(startTime,1));
				DateTime lastDay = DateUtil.beginOfDay(endTime);

				for (long time = nextDay.getTime(); time < lastDay.getTime(); time = DateUtil.offsetDay(new Date(time),1).getTime()){
					int weekOfYear = TimeTool.getWeekOfYear(team, time);
					int chineseWeek = TimeTool.getChineseWeek(time);
					if (lessonMap.get(chineseWeek) != null && lessonMap.get(chineseWeek).size() != 0){
						// 存在教课的老师
						for (Lesson lesson : lessonMap.get(chineseWeek)) {
							TeacherLeaveListBaseDO teacherLeaveListBaseDO =
									TeacherLeaveListBaseDO.builder()
									.applyId(leaveId)
									.teacherId(lesson.getTeacherId())
									.looked(false)
									.lessonId(lesson.getId())
									.weekNum(weekOfYear)
									.year(Integer.parseInt(team.split("-")[0]))
									.build();
							teacherLeaveListMapper.insert(teacherLeaveListBaseDO);
						}

					}
				}

				lessons = lessonMap.get(TimeTool.getChineseWeek(startTime.getTime()));
				for (Lesson lesson : lessons) {
					int week = TimeTool.getWeekOfYear(team, startTime.getTime());
					Date lessonEndTime = DateUtil.parse(DateUtil.format(startTime, "YYYY-MM-dd")+" "+lesson.getEndTime()+":00");

					if (startTime.getTime()<lessonEndTime.getTime() && lessonEndTime.getTime()<nextDay.getTime()){
						TeacherLeaveListBaseDO teacherLeaveListBaseDO =
								TeacherLeaveListBaseDO.builder()
								.applyId(leaveId)
								.teacherId(lesson.getTeacherId())
								.looked(false)
								.lessonId(lesson.getId())
								.weekNum(week)
								.year(Integer.parseInt(team.split("-")[0]))
								.build();
						teacherLeaveListMapper.insert(teacherLeaveListBaseDO);
					}
				}

				lessons = lessonMap.get(TimeTool.getChineseWeek(endTime.getTime()));
				for (Lesson lesson : lessons) {
					int week = TimeTool.getWeekOfYear(team, endTime.getTime());
					Date lessonStartTime = DateUtil.parse(DateUtil.format(endTime, "YYYY-MM-dd")+" "+lesson.getStartTime()+":00");

					if (lessonStartTime.getTime() > lastDay.getTime() && lessonStartTime.getTime() < endTime.getTime()){
						TeacherLeaveListBaseDO teacherLeaveListBaseDO =
								TeacherLeaveListBaseDO.builder()
										.applyId(leaveId)
										.teacherId(lesson.getTeacherId())
										.looked(false)
										.lessonId(lesson.getId())
										.weekNum(week)
										.year(Integer.parseInt(team.split("-")[0]))
										.build();
						teacherLeaveListMapper.insert(teacherLeaveListBaseDO);
					}
				}
			}
		}
		return num;
	}

	@Override
	@Transactional
	public int rejectApply(ManageLeaveList manageLeaveList) {
		int num = 0;
		ManageLeaveListBaseDO manageLeaveListBaseDO =
				ManageLeaveListBaseDO.builder()
						.id(manageLeaveList.getId())
						.allow(false)
						.build();
		if (collegeLeaveListMapper.updateByPrimaryKeySelective(manageLeaveListBaseDO) == 1){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
					.id(manageLeaveList.getId())
					.status(3)
					.build();
			num = leaveApplyMapper.updateByPrimaryKeySelective(leaveApplyDO);
		}
		return num;
	}
}
