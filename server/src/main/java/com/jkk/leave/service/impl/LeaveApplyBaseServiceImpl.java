package com.jkk.leave.service.impl;

import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.mapper.LeaveApplyMapper;
import com.jkk.leave.service.LeaveApplyBaseService;
import com.jkk.leave.tools.ApplyStatus;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplyBaseServiceImpl implements LeaveApplyBaseService {
	private final LeaveApplyMapper leaveApplyMapper;

	public LeaveApplyBaseServiceImpl(LeaveApplyMapper leaveApplyMapper) {
		this.leaveApplyMapper = leaveApplyMapper;
	}

	@Override
	public int setStatusById(Integer id, ApplyStatus status) {
		LeaveApplyDO leaveApplyDO =
				LeaveApplyDO.builder()
						.id(id)
						.status(status.getStatus())
						.build();
		return leaveApplyMapper.updateByPrimaryKeySelective(leaveApplyDO);
	}

	@Override
	public LeaveApplyDO getApplyByIdUnSafe(Integer id) {
		return leaveApplyMapper.selectByPrimaryKey(id);
	}
}
