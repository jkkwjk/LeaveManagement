package com.jkk.leave.service.impl;

import com.jkk.leave.entity.DO.AuthDO;
import com.jkk.leave.entity.VO.AuthVO;
import com.jkk.leave.mapper.AuthMapper;
import com.jkk.leave.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class AuthServiceImpl implements AuthService {
	private final AuthMapper authMapper;

	public AuthServiceImpl(AuthMapper authMapper) {
		this.authMapper = authMapper;
	}

	@Override
	public AuthVO getAuthById(Integer id) {
		List<AuthDO> authDOS = authMapper.getAuthById(id);
		AuthVO authVO = new AuthVO();
		authVO.setName(authDOS.get(0).getName());

		Map<String,List> map = new HashMap<>();
		for (AuthDO authDO : authDOS) {
			if (map.containsKey(authDO.getKey())){
				map.get(authDO.getKey()).add(authDO.getValue());
			}else {
				map.put(authDO.getKey(),new ArrayList());
				map.get(authDO.getKey()).add(authDO.getValue());
			}
		}
		authVO.setData(map);
		return authVO;
	}
}
