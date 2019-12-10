package com.jkk.leave.entity.POJO.base;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sorter {
	private String prop;
	/**
	 * 0 升序, 1降序
	 */
	private int desc;
}
