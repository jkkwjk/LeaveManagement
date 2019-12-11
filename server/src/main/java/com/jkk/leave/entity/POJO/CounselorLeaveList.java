package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CounselorLeaveList {
	private Integer id;
	@JsonProperty("number")
	private Integer studentId;
	@JsonProperty("name")
	private String studentName;
	private Long sendTime;
	private Long startTime;
	private Long endTime;
	private String type;
	private String detail;
	@JsonProperty("class")
	private Integer classes;
	private String showWhat;

	@JsonIgnore
	private Boolean looked;
	@JsonIgnore
	private Boolean allow;
	@JsonIgnore
	private String team;

}
