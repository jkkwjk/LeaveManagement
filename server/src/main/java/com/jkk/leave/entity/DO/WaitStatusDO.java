package com.jkk.leave.entity.DO;

import lombok.Data;

import java.util.List;

@Data
public class WaitStatusDO {
	private Integer id;
	private Integer counselorActive;
	private Integer collegeActive;

	@Data
	public static class TeacherActive{
		private Integer teacherId;
		private Boolean looked;
	}

	private List<TeacherActive> teacherActive;
}
