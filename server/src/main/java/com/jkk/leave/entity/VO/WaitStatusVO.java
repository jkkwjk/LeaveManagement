package com.jkk.leave.entity.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaitStatusVO {
	@Data
	public static class TeacherActive {
		private String name;
		private Boolean looked;
	}

	private Integer active;
	private List<TeacherActive> teachers;

}
