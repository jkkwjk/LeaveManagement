package com.jkk.leave.service.impl;

import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.*;
import com.jkk.leave.service.CounselorApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CounselorApplyServiceImpl implements CounselorApplyService {
	private final CounselorLeaveListMapper counselorLeaveListMapper;
	private final UserMapper userMapper;
	private final CollegeLeaveListMapper collegeLeaveListMapper;
	private final LeaveApplyMapper leaveApplyMapper;
	private final StudentInfoMapper studentInfoMapper;
	public CounselorApplyServiceImpl(CounselorLeaveListMapper counselorLeaveListMapper, UserMapper userMapper, CollegeLeaveListMapper collegeLeaveListMapper, LeaveApplyMapper leaveApplyMapper, StudentInfoMapper studentInfoMapper) {
		this.counselorLeaveListMapper = counselorLeaveListMapper;
		this.userMapper = userMapper;
		this.collegeLeaveListMapper = collegeLeaveListMapper;
		this.leaveApplyMapper = leaveApplyMapper;
		this.studentInfoMapper = studentInfoMapper;
	}

	@Override
	@Transactional
	public List<ManageLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<ManageLeaveList> manageLeaveLists = counselorLeaveListMapper.selectCustom(user.getId(), filter, sorter);

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
			// 进行下一步操作
			ManageLeaveListBaseDO manageLeaveListBaseDO =
					ManageLeaveListBaseDO.builder()
					.id(manageLeaveList.getId())
					.looked(false)
					.build();
			num = collegeLeaveListMapper.insert(manageLeaveListBaseDO);
		}
		return num;
	}

	@Override
	@Transactional
	public int rejectApply(ManageLeaveList manageLeaveList, User user) {
		int num = 0;
		manageLeaveList.setAllow(false);
		if (counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(manageLeaveList, user.getId()) == 1){
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
