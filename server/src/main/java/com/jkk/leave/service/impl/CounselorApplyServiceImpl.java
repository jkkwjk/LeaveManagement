package com.jkk.leave.service.impl;

import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.POJO.CounselorLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.CounselorLeaveListMapper;
import com.jkk.leave.mapper.UserMapper;
import com.jkk.leave.service.CounselorApplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CounselorApplyServiceImpl implements CounselorApplyService {
	private final CounselorLeaveListMapper counselorLeaveListMapper;
	private final UserMapper userMapper;
	public CounselorApplyServiceImpl(CounselorLeaveListMapper counselorLeaveListMapper, UserMapper userMapper) {
		this.counselorLeaveListMapper = counselorLeaveListMapper;
		this.userMapper = userMapper;
	}

	@Override
	@Transactional
	public List<CounselorLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<CounselorLeaveList> counselorLeaveLists = counselorLeaveListMapper.selectCustom(user.getId(), filter, sorter);

		for (CounselorLeaveList counselorLeaveList : counselorLeaveLists) {
			if (counselorLeaveList.getAllow() == null){
				counselorLeaveList.setShowWhat("button");
			}else if (counselorLeaveList.getAllow()){
				counselorLeaveList.setShowWhat("allow");
			}else {
				counselorLeaveList.setShowWhat("reject");
			}

			counselorLeaveList.setStudentName(userMapper.getUserInfoById(counselorLeaveList.getStudentId()).getName());
			// 已查看
			if (!counselorLeaveList.getLooked()){
				counselorLeaveList.setLooked(true);
				counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(counselorLeaveList, user.getId());
			}
		}
		return counselorLeaveLists;
	}

	@Override
	public int allowApply(CounselorLeaveList counselorLeaveList, User user) {
		counselorLeaveList.setAllow(true);
		return counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(counselorLeaveList, user.getId());
	}

	@Override
	public int rejectApply(CounselorLeaveList counselorLeaveList, User user) {
		counselorLeaveList.setAllow(false);
		return counselorLeaveListMapper.updateByPrimaryKeySelectiveSafe(counselorLeaveList, user.getId());
	}
}
