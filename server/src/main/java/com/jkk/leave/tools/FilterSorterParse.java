package com.jkk.leave.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;

import java.util.Map;

public final class FilterSorterParse {
	public static Filter parseFilter(String custom){
		Map<String, Object> root = (Map) JSON.parse(custom);
		JSONArray filterArray = (JSONArray) root.get("custom");

		Filter filter = new Filter();
		for (Object o : filterArray) {
			JSONObject obj = (JSONObject) o;
			filter.addFilter(ColumnMapTool.map.get(obj.getString("prop")), obj.getString("content"));
		}
		return filter;
	}

	public static Sorter parseSorter(String custom, String defaultSorter){
		Map<String, Object> root = (Map) JSON.parse(custom);
		JSONObject sort = (JSONObject) root.get("sort");

		Sorter sorter = null;
		if (!sort.get("prop").equals("") && sort.get("order") != null){
			sorter = new Sorter(ColumnMapTool.map.get(sort.getString("prop")),
					sort.get(("order")).equals("ascending")? 0:1);
		}else {
			sorter = new Sorter(ColumnMapTool.map.get(defaultSorter), 0);
		}
		return sorter;
	}
}
