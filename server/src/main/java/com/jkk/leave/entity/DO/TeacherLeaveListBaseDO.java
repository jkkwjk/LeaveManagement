package com.jkk.leave.entity.DO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherLeaveListBaseDO {
	private Integer id;
	private Integer applyId;
	private Integer teacherId;
	private Boolean looked;
	private String lessonId;
	private Integer weekNum;
	private Integer year;
}
