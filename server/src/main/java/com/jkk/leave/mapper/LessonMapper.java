package com.jkk.leave.mapper;

import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonMapper extends MyBatisBaseDao<ManageLeaveListBaseDO, String> {
	List<Lesson> getStudentAllLesson(@Param("studentId") Integer studentId, @Param("team") String team);

	List<Lesson> getTeacherAllLesson(@Param("teacherId") Integer teacherId, @Param("team") String team);
}
