package com.jkk.leave.service;

import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.tools.ApplyStatus;

public interface LeaveApplyBaseService {
	int setStatusById(Integer id, ApplyStatus status);

	LeaveApplyDO getApplyByIdUnSafe(Integer id);
}
