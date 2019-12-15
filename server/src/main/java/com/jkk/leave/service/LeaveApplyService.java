package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.VO.WaitStatusVO;

import java.util.List;

public interface LeaveApplyService {
	int addLeaveApply(LeaveApplyVO leaveApplyVO, User user);

	int apply2TrashBin(LeaveApplyVO leaveApplyVO, User user);

	int reductionApply(LeaveApplyVO leaveApplyVO, User user);

	int deleteApply(LeaveApplyVO leaveApplyVO, User user);

	int modifyApply(LeaveApplyVO leaveApplyVO, User user);

	int sendApply(LeaveApplyVO leaveApplyVO, User user);

	Boolean isSend(LeaveApplyVO leaveApplyVO, User user);

	Boolean isInTrashBin(LeaveApplyVO leaveApplyVO, User user);

	List<LeaveApplyVO> getApplyList(User user, int page, int num, Filter filters, Sorter sorter);

	List<LeaveApplyVO> getApplyListInTrashBin(User user);

	LeaveApplyVO getApplyById(Integer id, User user);

	List<String> getTeam();

	WaitStatusVO getLeaveStatus(Integer applyId, User user);

	List<ArchiveVO> getArchive(Long startTime, Long endTime, User user, Integer page, Integer num);
}
