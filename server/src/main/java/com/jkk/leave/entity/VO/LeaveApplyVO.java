package com.jkk.leave.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class LeaveApplyVO {
	private Integer id;
	private Long sendTime;
	private Long startTime;
	private Long endTime;
	private String type;
	private String detail;
	private String counselor;
	private String showWhat;
}
