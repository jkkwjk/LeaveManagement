package com.jkk.leave.service.impl;

import com.jkk.leave.entity.POJO.Card;
import com.jkk.leave.entity.POJO.Test;
import com.jkk.leave.mapper.CardMapper;
import com.jkk.leave.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
	private final CardMapper cardMapper;

	public TestServiceImpl(CardMapper cardMapper) {
		this.cardMapper = cardMapper;
	}

	@Override
	public Test getStr() {
		Test test = new Test();
		test.setMoney("1");
		test.setUserId(555);
		log.info("111 我成功了");
		return test;
	}

	@Override
	public Card selectById(int id) {
		return cardMapper.selectByPrimaryKey(id);
	}
}
