package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class Lesson {
	@JsonProperty("prop")
	private String id;
	@JsonIgnore
	private String team;
	@JsonIgnore
	private Integer teacherId;
	@JsonProperty("title")
	private String name;
	@JsonIgnore
	private Integer week;
	@JsonIgnore
	private Integer timeId;
	@JsonIgnore
	private String startTime;
	@JsonIgnore
	private String endTime;
}
