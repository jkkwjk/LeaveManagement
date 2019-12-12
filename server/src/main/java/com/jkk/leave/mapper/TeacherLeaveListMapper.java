package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.TeacherLeaveListBaseDO;
import com.jkk.leave.entity.POJO.TeacherLeaveList;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherLeaveListMapper extends MyBatisBaseDao<TeacherLeaveListBaseDO, Integer> {
	List<TeacherLeaveList> selectCustom(@Param("week")Integer week, @Param("year")Integer year, @Param("lesson")String lessonId, @Param("teacherId") Integer teacherId);
}
