package com.jkk.leave.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.ChartNumDO;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.entity.VO.ChartNumVO;
import com.jkk.leave.mapper.*;
import com.jkk.leave.service.*;
import com.jkk.leave.tools.ApplyStatus;
import com.jkk.leave.tools.MailTool;
import com.jkk.leave.tools.TimeTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CollegeApplyServiceImpl implements CollegeApplyService {
	private final LeaveApplyBaseService leaveApplyBaseService;
	private final TeacherApplyService teacherApplyService;
	private final LessonService lessonService;
	private final StudentInfoService studentInfoService;
	private final UserService userService;

	private final CollegeLeaveListMapper collegeLeaveListMapper;
	public CollegeApplyServiceImpl(LeaveApplyBaseService leaveApplyBaseService, CollegeLeaveListMapper collegeLeaveListMapper, TeacherApplyService teacherApplyService, StudentInfoService studentInfoService, UserService userService, LessonService lessonService) {
		this.leaveApplyBaseService = leaveApplyBaseService;
		this.collegeLeaveListMapper = collegeLeaveListMapper;
		this.teacherApplyService = teacherApplyService;
		this.studentInfoService = studentInfoService;
		this.userService = userService;
		this.lessonService = lessonService;
	}


	@Override
	@Transactional
	public List<ManageLeaveList> getApply(int page, int num, Filter filter, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<ManageLeaveList> manageLeaveLists = collegeLeaveListMapper.selectCustom(filter, sorter);

		for (ManageLeaveList manageLeaveList : manageLeaveLists) {
			manageLeaveList = manageLeaveList.parseShowWhat();

			manageLeaveList.setStudentName(userService.getUserName(manageLeaveList.getStudentId()));
			manageLeaveList.setClasses(studentInfoService.getStudentClass(manageLeaveList.getStudentId()));
			manageLeaveList.setCounselorName(studentInfoService.getStudentCounselorName(manageLeaveList.getStudentId()));
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
			if (leaveApplyBaseService.setStatusById(leaveId, ApplyStatus.AGREE) == 1){
				// 添加到课程老师中
				num = 1;
				String team = TimeTool.getThisTeam();
				LeaveApplyDO selectLeaveApply = leaveApplyBaseService.getApplyByIdUnSafe(leaveId);
				List<Lesson> allLesson = lessonService.getStudentLessonInTeam(selectLeaveApply.getStudentId(), team);
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
							teacherApplyService.addApply(teacherLeaveListBaseDO);
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
						teacherApplyService.addApply(teacherLeaveListBaseDO);
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
						teacherApplyService.addApply(teacherLeaveListBaseDO);
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
			num = leaveApplyBaseService.setStatusById(manageLeaveList.getId(), ApplyStatus.REJECT);
		}

		return num;
	}

	@Override
	public int addApply(Integer applyId) {
		int num = 0;
		ManageLeaveListBaseDO manageLeaveListBaseDO =
				ManageLeaveListBaseDO.builder()
						.id(applyId)
						.looked(false)
						.build();
		if (collegeLeaveListMapper.insert(manageLeaveListBaseDO) == 1){
			LeaveApplyDO leaveApplyDO = leaveApplyBaseService.getApplyByIdUnSafe(applyId);
			num = MailTool.sendEmail(userService.getUserById(
					studentInfoService.getStudentCounselorId(leaveApplyDO.getStudentId())
			).getEMail(), leaveApplyDO);
		}
		return num;
	}

	@Override
	public List<ArchiveVO> getArchive(Long startTime, Long endTime, Integer page, Integer num) {
		if (page != null && num != null){
			PageHelper.startPage(page, num);
		}
		List<ManageLeaveList> manageLeaveLists = collegeLeaveListMapper.selectArchive(startTime, endTime);

		List<ArchiveVO> ret = new ArrayList<>();
		for (ManageLeaveList manageLeaveList : manageLeaveLists) {
			Integer studentId = manageLeaveList.getStudentId();
			ArchiveVO archiveVO =
					ArchiveVO.builder()
							.studentId(studentId)
							.studentName(userService.getUserName(studentId))
							.counselor(studentInfoService.getStudentCounselorName(studentId))
							.classes(studentInfoService.getStudentClass(studentId))
							.detail(manageLeaveList.getDetail())
							.type(manageLeaveList.getType())
							.showWhat(manageLeaveList.parseShowWhat().getShowWhat())
							.sendTime(DateUtil.format(new Date(manageLeaveList.getSendTime()),"yyyy-MM-dd HH:mm:ss"))
							.startTime(DateUtil.format(new Date(manageLeaveList.getStartTime()),"yyyy-MM-dd HH:mm:ss"))
							.endTime(DateUtil.format(new Date(manageLeaveList.getEndTime()),"yyyy-MM-dd HH:mm:ss"))
							.build();
			ret.add(archiveVO);
		}

		return ret;
	}

	@Override
	public Integer getSummaryBetWeenTime(Long startTime, Long endTime) {
		return collegeLeaveListMapper.getCountBetweenTime(startTime, endTime);
	}

	@Override
	public List<ChartMap> getMapChartBetWeenTime(Long startTime, Long endTime) {
		return collegeLeaveListMapper.getMapChartBetWeenTime(startTime, endTime);
	}

	@Override
	public List<ChartNumVO> getNumChartBetWeenTime(Long startTime, Long endTime) {
		List<ChartNumDO> chartNumDOList = collegeLeaveListMapper.getNumChartBetWeenTime(startTime, endTime);
		Map<String, int[]> typeMap = new ConcurrentHashMap<>();
		typeMap.put("公假", new int[7]);
		typeMap.put("事假", new int[7]);
		typeMap.put("病假", new int[7]);
		for (ChartNumDO chartNumDO : chartNumDOList) {
			int week = chartNumDO.getWeek()==0? 6:chartNumDO.getWeek()-1;
			typeMap.get(chartNumDO.getType())[week] += chartNumDO.getNum();
		}

		List<ChartNumVO> ret = new ArrayList<>();
		for (String s : typeMap.keySet()) {
			ret.add(new ChartNumVO(s, typeMap.get(s)));
		}
		return ret;
	}
}
