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
public interface CollegeLeaveListMapper extends MyBatisBaseDao<ManageLeaveListBaseDO, Integer> {
	List<ManageLeaveList> selectCustom(@Param("filters") Filter filters, @Param("sorter") Sorter sorter);

	List<ManageLeaveList> selectArchive(@Param("start_time")Long startTime, @Param("end_time")Long endTime);

	Integer getCountBetweenTime(@Param("start_time")Long startTime, @Param("end_time")Long endTime);

	List<ChartMap> getMapChartBetWeenTime(@Param("start_time")Long startTime, @Param("end_time")Long endTime);

	List<ChartNumDO> getNumChartBetWeenTime(@Param("start_time")Long startTime, @Param("end_time")Long endTime);
}
