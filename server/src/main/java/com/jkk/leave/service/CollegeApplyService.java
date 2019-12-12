package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;

import java.util.List;

public interface CollegeApplyService {
	List<ManageLeaveList> getApply(int page, int num, Filter filter, Sorter sorter);

	int allowApply(ManageLeaveList manageLeaveList);

	int rejectApply(ManageLeaveList manageLeaveList);
}
