package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TeacherLeaveList {
	@JsonIgnore
	private Integer id;
	@JsonIgnore
	private Boolean looked;

	@JsonProperty("number")
	private Integer studentId;
	@JsonProperty("name")
	private String studentName;
	@JsonProperty("counselor")
	private String counselorName;
	private String type;
	private String detail;
	@JsonProperty("class")
	private Integer classes;
}
