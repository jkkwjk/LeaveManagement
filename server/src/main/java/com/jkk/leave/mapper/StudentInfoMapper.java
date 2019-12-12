package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.LessonDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentInfoMapper {
	Integer getCounselorId(@Param("studentId") Integer studentId);

	Integer getClass(@Param("studentId") Integer studentId);

	List<LessonDO> getAllLesson(@Param("studentId") Integer studentId,@Param("team") String team);
}
