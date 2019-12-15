package com.jkk.leave.entity.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartNumVO {
	@JsonProperty("t")
	private String type;
	@JsonProperty("v")
	private int[] num;
}
