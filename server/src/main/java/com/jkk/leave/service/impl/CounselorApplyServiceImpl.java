package com.jkk.leave.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.ChartNumDO;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.entity.VO.ChartNumVO;
import com.jkk.leave.mapper.*;
import com.jkk.leave.service.*;
import com.jkk.leave.tools.ApplyStatus;
import com.jkk.leave.tools.MailTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounselorApplyServiceImpl implements CounselorApplyService {
	private final CollegeApplyService collegeApplyService;
	private final LeaveApplyBaseService leaveApplyBaseService;
	private final StudentInfoService studentInfoService;
	private final UserService userService;

	private final CounselorLeaveListMapper counselorLeaveListMapper;
	public CounselorApplyServiceImpl(CounselorLeaveListMapper counselorLeaveListMapper, UserService userService, CollegeApplyService collegeApplyService, LeaveApplyBaseService leaveApplyBaseService, StudentInfoService studentInfoService) {
		this.counselorLeaveListMapper = counselorLeaveListMapper;
		this.userService = userService;
		this.collegeApplyService = collegeApplyService;
		this.leaveApplyBaseService = leaveApplyBaseService;
		this.studentInfoService = studentInfoService;
	}

	@Override
	@Transactional
	public List<ManageLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<ManageLeaveList> manageLeaveLists = counselorLeaveListMapper.selectCustom(user.getId(), filter, sorter);

		for (ManageLeaveList manageLeaveList : manageLeaveLists) {
			manageLeaveList = manageLeaveList.parseShowWhat();

			manageLeaveList.setStudentName(userService.getUserName(manageLeaveList.getStudentId()));
			manageLeaveList.setClasses(studentInfoService.getStudentClass(manageLeaveList.getStudentId()));
			// 已查看
			if (!manageLeaveList.getLooked()){
				manageLeaveList.setLooked(true);
				counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(manageLeaveList, user.getId());
			}
		}
		return manageLeaveLists;
	}

	@Override
	@Transactional
	public int allowApply(ManageLeaveList manageLeaveList, User user) {
		int num = 0;
		manageLeaveList.setAllow(true);
		if (counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(manageLeaveList, user.getId()) == 1){
			num = collegeApplyService.addApply(manageLeaveList.getId());
		}
		return num;
	}

	@Override
	@Transactional
	public int rejectApply(ManageLeaveList manageLeaveList, User user) {
		int num = 0;
		manageLeaveList.setAllow(false);
		if (counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(manageLeaveList, user.getId()) == 1){
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
		if (counselorLeaveListMapper.insert(manageLeaveListBaseDO) == 1){
			LeaveApplyDO leaveApplyDO = leaveApplyBaseService.getApplyByIdUnSafe(applyId);
			num = MailTool.sendEmail(userService.getUserById(
					studentInfoService.getStudentCounselorId(leaveApplyDO.getStudentId())
			).getEMail(), leaveApplyDO);
		}


		return num;
	}

	@Override
	public List<ArchiveVO> getArchive(Long startTime, Long endTime, User user, Integer page, Integer num) {
		if (page != null && num != null){
			PageHelper.startPage(page, num);
		}

		List<ManageLeaveList> manageLeaveLists = counselorLeaveListMapper.selectArchive(user.getId(), startTime, endTime);

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
	public Integer getSummaryBetWeenTime(Long startTime, Long endTime, User user) {
		return counselorLeaveListMapper.getCountBetweenTime(user.getId(), startTime, endTime);
	}

	@Override
	public List<ChartMap> getMapChartBetWeenTime(Long startTime, Long endTime, User user) {
		return counselorLeaveListMapper.getMapChartBetWeenTime(user.getId(), startTime, endTime);
	}

	@Override
	public List<ChartNumVO> getNumChartBetWeenTime(Long startTime, Long endTime, User user) {
		List<ChartNumDO> chartNumDOList = counselorLeaveListMapper.getNumChartBetWeenTime(user.getId(), startTime, endTime);
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
