package com.jkk.leave.service;

import com.jkk.leave.entity.POJO.Card;
import com.jkk.leave.entity.POJO.Test;

import javax.validation.constraints.NotNull;

public interface TestService {
	Test getStr();
	Card selectById(@NotNull int id);
}
