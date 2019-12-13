package com.jkk.leave.service.impl;

import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.mapper.LeaveApplyMapper;
import com.jkk.leave.service.CounselorApplyService;
import com.jkk.leave.service.LeaveApplyService;
import com.jkk.leave.service.StudentInfoService;
import com.jkk.leave.tools.ApplyStatus;
import com.jkk.leave.tools.TimeTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 可以确保 userID一定是student
 */
@Service
public class LeaveApplyServiceImpl implements LeaveApplyService {
	private final LeaveApplyMapper leaveApplyMapper;
	private final CounselorApplyService counselorApplyService;
	private final StudentInfoService studentInfoService;

	public LeaveApplyServiceImpl(LeaveApplyMapper leaveApplyMapper, CounselorApplyService counselorApplyService, StudentInfoService studentInfoService) {
		this.leaveApplyMapper = leaveApplyMapper;
		this.counselorApplyService = counselorApplyService;
		this.studentInfoService = studentInfoService;
	}

	@Override
	public int addLeaveApply(LeaveApplyVO leaveApplyVO, User user) {
		LeaveApplyDO leaveApplyDO =
				LeaveApplyDO.builder()
				.startTime(leaveApplyVO.getStartTime())
				.endTime(leaveApplyVO.getEndTime())
				.studentId(user.getId())
				.delStatus(false)
				.type(leaveApplyVO.getType())
				.detail(leaveApplyVO.getDetail())
				.status(ApplyStatus.UN_SEND.getStatus())
				.team(TimeTool.getThisTeam())
				.build();
		int num = leaveApplyMapper.insertSelective(leaveApplyDO);
		if (num == 1){
			return leaveApplyDO.getId();
		}else {
			return num;
		}
	}

	@Override
	public int apply2TrashBin(LeaveApplyVO leaveApplyVO, User user) {
		if (!this.isSend(leaveApplyVO, user) && !this.isInTrashBin(leaveApplyVO, user)){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
							.id(leaveApplyVO.getId())
							.studentId(user.getId())
							.delStatus(true)
							.build();
			return leaveApplyMapper.updateByPrimaryKeyAndStuIdSelective(leaveApplyDO);
		}else {
			return 0;
		}
	}

	@Override
	public int reductionApply(LeaveApplyVO leaveApplyVO, User user) {
		if (!this.isSend(leaveApplyVO, user) && this.isInTrashBin(leaveApplyVO, user)){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
							.id(leaveApplyVO.getId())
							.studentId(user.getId())
							.delStatus(false)
							.build();
			return leaveApplyMapper.updateByPrimaryKeyAndStuIdSelective(leaveApplyDO);
		}else {
			return 0;
		}
	}

	@Override
	public int deleteApply(LeaveApplyVO leaveApplyVO, User user) {
		if (!this.isSend(leaveApplyVO, user) && this.isInTrashBin(leaveApplyVO, user)){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
							.id(leaveApplyVO.getId())
							.studentId(user.getId())
							.build();
			return leaveApplyMapper.deleteByPrimaryKeyAndStuId(leaveApplyDO);
		}else {
			return 0;
		}
	}

	@Override
	public int modifyApply(LeaveApplyVO leaveApplyVO, User user) {
		if (!this.isSend(leaveApplyVO, user) && !this.isInTrashBin(leaveApplyVO, user)){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
							.id(leaveApplyVO.getId())
							.studentId(user.getId())
							.startTime(leaveApplyVO.getStartTime())
							.endTime(leaveApplyVO.getEndTime())
							.type(leaveApplyVO.getType())
							.detail(leaveApplyVO.getDetail())
							.build();
			return leaveApplyMapper.updateByPrimaryKeyAndStuIdSelective(leaveApplyDO);
		}else {
			return 0;
		}
	}

	@Override
	@Transactional
	public int sendApply(LeaveApplyVO leaveApplyVO, User user) {
		int resNum = 0;
		if (!this.isInTrashBin(leaveApplyVO, user)){
			LeaveApplyDO leaveApplyDO =
					LeaveApplyDO.builder()
							.id(leaveApplyVO.getId())
							.studentId(user.getId())
							.sendTime(new Date().getTime())
							.status(ApplyStatus.WAIT.getStatus())
							.build();

			if (leaveApplyMapper.updateByPrimaryKeyAndStuIdSelective(leaveApplyDO) == 1){
				resNum = counselorApplyService.addApply(leaveApplyVO.getId());
			}

		}
		return resNum;
	}

	@Override
	public Boolean isSend(LeaveApplyVO leaveApplyVO, User user) {
		LeaveApplyDO leaveApplyDO = leaveApplyMapper.selectByPrimaryKey(leaveApplyVO.getId());
		return leaveApplyDO != null && leaveApplyDO.getStudentId().equals(user.getId()) && leaveApplyDO.getStatus()!=0;
	}

	@Override
	public Boolean isInTrashBin(LeaveApplyVO leaveApplyVO, User user) {
		LeaveApplyDO leaveApplyDO = leaveApplyMapper.selectByPrimaryKey(leaveApplyVO.getId());
		return leaveApplyDO != null && leaveApplyDO.getStudentId().equals(user.getId()) && leaveApplyDO.getDelStatus();
	}

	@Override
	public List<LeaveApplyVO> getApplyList(User user, int page, int num, Filter filters, Sorter sorter) {
		PageHelper.startPage(page, num);
		List<LeaveApplyDO> leaveApplyDOS = leaveApplyMapper.selectCustom(user.getId(), filters, sorter, false);

		List<LeaveApplyVO> ret = new ArrayList<>();
		for (LeaveApplyDO leaveApplyDO : leaveApplyDOS) {
			LeaveApplyVO leaveApplyVO =
					LeaveApplyVO.builder()
					.id(leaveApplyDO.getId())
					.sendTime(leaveApplyDO.getSendTime())
					.startTime(leaveApplyDO.getStartTime())
					.endTime(leaveApplyDO.getEndTime())
					.type(leaveApplyDO.getType())
					.detail(leaveApplyDO.getDetail())
					.counselor(studentInfoService.getStudentCounselorName(leaveApplyDO.getStudentId()))
					.showWhat(parseShowWhat(leaveApplyDO.getStatus()))
					.build();

			ret.add(leaveApplyVO);
		}

		return ret;
	}

	@Override
	public List<LeaveApplyVO> getApplyListInTrashBin(User user) {
		List<LeaveApplyDO> leaveApplyDOS = leaveApplyMapper.selectCustom(user.getId(),null,null,true);
		List<LeaveApplyVO> ret = new ArrayList<>();
		for (LeaveApplyDO leaveApplyDO : leaveApplyDOS) {
			LeaveApplyVO leaveApplyVO =
					LeaveApplyVO.builder()
							.id(leaveApplyDO.getId())
							.startTime(leaveApplyDO.getStartTime())
							.endTime(leaveApplyDO.getEndTime())
							.type(leaveApplyDO.getType())
							.detail(leaveApplyDO.getDetail())
							.build();

			ret.add(leaveApplyVO);
		}

		return ret;
	}

	@Override
	public LeaveApplyVO getApplyById(Integer id, User user) {
		LeaveApplyDO leaveApplyDO = leaveApplyMapper.selectByPrimaryKey(id);
		if (user.getId().equals(leaveApplyDO.getStudentId())){
			return LeaveApplyVO.builder()
					.id(leaveApplyDO.getId())
					.sendTime(leaveApplyDO.getSendTime())
					.startTime(leaveApplyDO.getStartTime())
					.endTime(leaveApplyDO.getEndTime())
					.type(leaveApplyDO.getType())
					.detail(leaveApplyDO.getDetail())
					.counselor(studentInfoService.getStudentCounselorName(leaveApplyDO.getStudentId()))
					.showWhat(parseShowWhat(leaveApplyDO.getStatus()))
					.build();
		}else {
			return null;
		}

	}

	@Override
	public List<String> getTeam() {
		return leaveApplyMapper.selectAllTeam();
	}

	private String parseShowWhat(int status){
		String showWhat = null;
		switch (status){
			case 0: showWhat = "button"; break;
			case 1: showWhat = "wait"; break;
			case 2: showWhat = "allow"; break;
			case 3: showWhat = "reject"; break;
		}
		return showWhat;
	}
}
