package com.jkk.leave.entity.DO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CounselorLeaveListBaseDO {
	private Integer id;
	private Boolean looked;
	private Boolean allow;
}
