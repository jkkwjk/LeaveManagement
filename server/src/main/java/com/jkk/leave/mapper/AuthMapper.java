package com.jkk.leave.mapper;

import com.jkk.leave.entity.DO.AuthDO;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AuthMapper {
	List<AuthDO> getAuthById(Integer id);
}
