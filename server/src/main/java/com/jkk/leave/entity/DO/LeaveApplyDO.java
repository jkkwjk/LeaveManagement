package com.jkk.leave.entity.DO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaveApplyDO {
	private Integer id;
	private Long sendTime;
	private Long startTime;
	private Long endTime;
	private Integer studentId;
	private Boolean delStatus;
	private String type;
	private String detail;
	private Integer status;
	private String team;
}
