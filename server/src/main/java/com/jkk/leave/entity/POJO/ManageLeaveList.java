package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManageLeaveList {
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
	@JsonProperty("counselor")
	private String counselorName;

	@JsonIgnore
	private Boolean looked;
	@JsonIgnore
	private Boolean allow;
	@JsonIgnore
	private String team;

	public ManageLeaveList parseShowWhat(){
		if (this.getAllow() == null){
			this.setShowWhat("button");
		}else if (this.getAllow()){
			this.setShowWhat("allow");
		}else {
			this.setShowWhat("reject");
		}
		return this;
	}

}
