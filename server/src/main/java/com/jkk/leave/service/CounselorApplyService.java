package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.POJO.ManageLeaveList;

import java.util.List;

public interface CounselorApplyService {
	List<ManageLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter);

	int allowApply(ManageLeaveList manageLeaveList, User user);

	int rejectApply(ManageLeaveList manageLeaveList, User user);
}
