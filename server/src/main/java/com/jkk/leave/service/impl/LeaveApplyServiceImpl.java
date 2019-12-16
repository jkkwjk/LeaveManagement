package com.jkk.leave.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.DO.WaitStatusDO;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.VO.WaitStatusVO;
import com.jkk.leave.mapper.LeaveApplyMapper;
import com.jkk.leave.service.CounselorApplyService;
import com.jkk.leave.service.LeaveApplyService;
import com.jkk.leave.service.StudentInfoService;
import com.jkk.leave.service.UserService;
import com.jkk.leave.tools.ApplyStatus;
import com.jkk.leave.tools.TimeTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可以确保 userID一定是student
 */
@Service
public class LeaveApplyServiceImpl implements LeaveApplyService {
	private final LeaveApplyMapper leaveApplyMapper;
	private final CounselorApplyService counselorApplyService;
	private final StudentInfoService studentInfoService;
	private final UserService userService;
	public LeaveApplyServiceImpl(LeaveApplyMapper leaveApplyMapper, CounselorApplyService counselorApplyService, StudentInfoService studentInfoService, UserService userService) {
		this.leaveApplyMapper = leaveApplyMapper;
		this.counselorApplyService = counselorApplyService;
		this.studentInfoService = studentInfoService;
		this.userService = userService;
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

	@Override
	public WaitStatusVO getLeaveStatus(Integer applyId, User user) {
		WaitStatusDO waitStatusDO = leaveApplyMapper.selectApplyStatus(user.getId(), applyId);
		int active = 1;
		if (waitStatusDO.getCounselorActive() != null){
			active++;
		}
		if (waitStatusDO.getCollegeActive() != null){
			active++;
		}
		List<WaitStatusVO.TeacherActive> list = null;
		if (waitStatusDO.getTeacherActive().size() != 0){
			list = new ArrayList<>();
			active++;
			Map<Integer, Boolean> idToLooked = new ConcurrentHashMap<>();
			for (WaitStatusDO.TeacherActive teacherActive : waitStatusDO.getTeacherActive()) {
				int teacherId = teacherActive.getTeacherId();
				if (idToLooked.containsKey(teacherId)){
					if (idToLooked.get(teacherId)){
						idToLooked.put(teacherId,true);
					}
				}else {
					idToLooked.put(teacherId,true);
				}
			}
			for (Integer integer : idToLooked.keySet()) {
				WaitStatusVO.TeacherActive teacherActive = new WaitStatusVO.TeacherActive();
				teacherActive.setLooked(idToLooked.get(integer));
				teacherActive.setName(userService.getUserName(integer));
			}
		}

		return WaitStatusVO.builder()
		.active(active)
		.teachers(list)
		.build();
	}

	@Override
	public List<ArchiveVO> getArchive(Long startTime, Long endTime, User user , Integer page, Integer num) {
		if (page != null && num != null){
			PageHelper.startPage(page, num);
		}
		List<LeaveApplyDO> leaveApplyDOList = leaveApplyMapper.selectArchive(user.getId(), startTime, endTime);

		List<ArchiveVO> ret = new ArrayList<>();
		for (LeaveApplyDO leaveApplyDO : leaveApplyDOList) {
			Integer studentId = leaveApplyDO.getStudentId();
			ArchiveVO archiveVO =
					ArchiveVO.builder()
					.studentId(studentId)
					.studentName(userService.getUserName(studentId))
					.counselor(studentInfoService.getStudentCounselorName(studentId))
					.classes(studentInfoService.getStudentClass(studentId))
					.detail(leaveApplyDO.getDetail())
					.type(leaveApplyDO.getType())
					.showWhat(this.parseShowWhat(leaveApplyDO.getStatus()))
					.sendTime(DateUtil.format(new Date(leaveApplyDO.getSendTime()),"yyyy-MM-dd HH:mm:ss"))
					.startTime(DateUtil.format(new Date(leaveApplyDO.getStartTime()),"yyyy-MM-dd HH:mm:ss"))
					.endTime(DateUtil.format(new Date(leaveApplyDO.getEndTime()),"yyyy-MM-dd HH:mm:ss"))
					.build();
			ret.add(archiveVO);
		}

		return ret;
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
