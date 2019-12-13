package com.jkk.leave.service.impl;

import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.*;
import com.jkk.leave.service.*;
import com.jkk.leave.tools.ApplyStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
		ManageLeaveListBaseDO manageLeaveListBaseDO =
				ManageLeaveListBaseDO.builder()
						.id(applyId)
						.looked(false)
						.build();
		return counselorLeaveListMapper.insert(manageLeaveListBaseDO);
	}
}
