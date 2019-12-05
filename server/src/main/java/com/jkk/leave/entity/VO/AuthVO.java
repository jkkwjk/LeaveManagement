package com.jkk.leave.entity.VO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AuthVO {
	private String name;
	private Map<String, List> data;
}