package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.POJO.CounselorLeaveList;

import java.util.List;

public interface CounselorApplyService {
	List<CounselorLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter);

	int allowApply(CounselorLeaveList counselorLeaveList, User user);

	int rejectApply(CounselorLeaveList counselorLeaveList, User user);
}
