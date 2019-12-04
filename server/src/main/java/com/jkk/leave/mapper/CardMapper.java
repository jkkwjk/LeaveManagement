package com.jkk.leave.mapper;

import com.jkk.leave.entity.POJO.Card;
import com.jkk.leave.mapper.base.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

/**
 * CardMapper继承基类
 */
@Repository
public interface CardMapper extends MyBatisBaseDao<Card, Integer> {
}