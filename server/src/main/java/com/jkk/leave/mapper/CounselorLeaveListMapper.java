package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.ChartNumDO;
import com.jkk.leave.entity.DO.ManageLeaveListBaseDO;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselorLeaveListMapper extends MyBatisBaseDao<ManageLeaveListBaseDO, Integer> {
	int updateByPrimaryKeySelectiveSafe(@Param("data") ManageLeaveList manageLeaveList, @Param("userId") Integer userId);

	ManageLeaveList selectByPrimaryKeySafe(@Param("id") Integer id, @Param("userId") Integer userId);

	List<ManageLeaveList> selectCustom(@Param("userId") Integer userId, @Param("filters") Filter filters, @Param("sorter") Sorter sorter);

	List<ManageLeaveList> selectArchive(@Param("counselorId")Integer counselorId, @Param("start_time")Long startTime, @Param("end_time")Long endTime);

	Integer getCountBetweenTime(@Param("counselorId")Integer counselorId, @Param("start_time")Long startTime, @Param("end_time")Long endTime);

	List<ChartMap> getMapChartBetWeenTime(@Param("counselorId")Integer counselorId, @Param("start_time")Long startTime, @Param("end_time")Long endTime);

	List<ChartNumDO> getNumChartBetWeenTime(@Param("counselorId")Integer counselorId, @Param("start_time")Long startTime, @Param("end_time")Long endTime);
}
