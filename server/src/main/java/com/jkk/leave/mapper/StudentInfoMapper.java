package com.jkk.leave.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoMapper {
	Integer getCounselorId(@Param("studentId") Integer studentId);

	Integer getClass(@Param("studentId") Integer studentId);
}
