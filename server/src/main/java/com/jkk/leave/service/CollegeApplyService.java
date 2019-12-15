package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.entity.VO.ChartNumVO;

import java.util.List;

public interface CollegeApplyService {
	List<ManageLeaveList> getApply(int page, int num, Filter filter, Sorter sorter);

	int allowApply(ManageLeaveList manageLeaveList);

	int rejectApply(ManageLeaveList manageLeaveList);

	int addApply(Integer applyId);

	List<ArchiveVO> getArchive(Long startTime, Long endTime, Integer page, Integer num);

	Integer getSummaryBetWeenTime(Long startTime, Long endTime);

	List<ChartMap> getMapChartBetWeenTime(Long startTime, Long endTime);

	List<ChartNumVO> getNumChartBetWeenTime(Long startTime, Long endTime);
}
