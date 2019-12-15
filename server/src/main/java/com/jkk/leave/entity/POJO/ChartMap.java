package com.jkk.leave.entity.POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartMap {
	@JsonProperty("p")
	private String position;
	@JsonProperty("n")
	private Integer leaveNum;
}
