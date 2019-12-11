package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.CounselorLeaveListBaseDO;
import com.jkk.leave.entity.POJO.CounselorLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselorLeaveListMapper extends MyBatisBaseDao<CounselorLeaveListBaseDO, Integer> {
	int updateByPrimaryKeySelectiveSafe(@Param("data") CounselorLeaveList counselorLeaveList,@Param("userId") Integer userId);

	CounselorLeaveList selectByPrimaryKeySafe(@Param("id") Integer id,@Param("userId") Integer userId);

	List<CounselorLeaveList> selectCustom(@Param("userId") Integer userId, @Param("filters") Filter filters, @Param("sorter") Sorter sorter);
}
