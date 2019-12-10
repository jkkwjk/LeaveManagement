package com.jkk.leave.entity.POJO.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * prop的值为value
 */
@Data
public class Filter {
	private Map<String, List<String>> map = new HashMap<>();

	public void addFilter(String prop, String value){
		if (map.containsKey(prop)){
			map.get(prop).add(value);
		}else {
			map.put(prop, new ArrayList<>());
			map.get(prop).add(value);
		}
	}

}
