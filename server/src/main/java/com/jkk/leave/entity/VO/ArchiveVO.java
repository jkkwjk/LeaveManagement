package com.jkk.leave.entity.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArchiveVO {
	private String sendTime;
	@JsonProperty("number")
	private Integer studentId;
	@JsonProperty("name")
	private String studentName;
	@JsonProperty("class")
	private Integer classes;
	private String counselor;
	private String type;
	private String detail;
	private String startTime;
	private String endTime;

	private String showWhat;
}
