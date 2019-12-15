package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.entity.VO.ChartNumVO;

import java.util.List;

public interface CounselorApplyService {
	List<ManageLeaveList> getApply(User user, int page, int num, Filter filter, Sorter sorter);

	int allowApply(ManageLeaveList manageLeaveList, User user);

	int rejectApply(ManageLeaveList manageLeaveList, User user);

	int addApply(Integer applyId);

	List<ArchiveVO> getArchive(Long startTime, Long endTime, User user , Integer page, Integer num);

	Integer getSummaryBetWeenTime(Long startTime, Long endTime, User user);

	List<ChartMap> getMapChartBetWeenTime(Long startTime, Long endTime, User user);

	List<ChartNumVO> getNumChartBetWeenTime(Long startTime, Long endTime, User user);
}
