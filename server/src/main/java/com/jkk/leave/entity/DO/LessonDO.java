package com.jkk.leave.entity.DO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonDO {
	private String id;
	private String team;
	private Integer teacherId;
	private String name;
	private Integer week;
	private Integer timeId;

	private String startTime;
	private String endTime;
}
