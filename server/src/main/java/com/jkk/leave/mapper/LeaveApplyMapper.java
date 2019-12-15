package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.LeaveApplyDO;
import com.jkk.leave.entity.DO.WaitStatusDO;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LeaveApplyMapper extends MyBatisBaseDao<LeaveApplyDO, Integer> {
	int updateByPrimaryKeyAndStuIdSelective(LeaveApplyDO leaveApplyDO);

	int deleteByPrimaryKeyAndStuId(LeaveApplyDO leaveApplyDO);

	List<LeaveApplyDO> selectCustom(@Param("studentId") Integer userId, @Param("filters") Filter filters, @Param("sorter") Sorter sorter, @Param("isDel")Boolean isDel);

	List<String> selectAllTeam();

	WaitStatusDO selectApplyStatus(@Param("studentId")Integer studentId,@Param("applyId")Integer applyId);

	List<LeaveApplyDO> selectArchive(@Param("studentId")Integer studentId, @Param("start_time")Long startTime, @Param("end_time")Long endTime);
}
