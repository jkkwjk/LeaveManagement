package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.service.CollegeApplyService;
import com.jkk.leave.tools.FilterSorterParse;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/col")
public class CollegeLeaveController {
	private final CollegeApplyService collegeApplyService;

	public CollegeLeaveController(CollegeApplyService collegeApplyService) {
		this.collegeApplyService = collegeApplyService;
	}


	@PostMapping
	public RestfulRes<List<ManageLeaveList>> getLeaveApply(Integer page, Integer num, String custom){
		Sorter sorter = FilterSorterParse.parseSorter(custom,"showWhat");
		Filter filter = FilterSorterParse.parseFilter(custom);

		return RestfulRes.success(collegeApplyService.getApply(page, num, filter, sorter));
	}

	@PostMapping("/{allow}/{id}")
	public RestfulRes addLeaveApply(@PathVariable String allow, @PathVariable String id){
		ManageLeaveList manageLeaveList = new ManageLeaveList();
		manageLeaveList.setId(Integer.parseInt(id));

		int num = 0;
		if (allow.equals("allow")){
			num = collegeApplyService.allowApply(manageLeaveList);
		}else if (allow.equals("reject")){
			num = collegeApplyService.rejectApply(manageLeaveList);
		}

		if (num == 1){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("请求失败");
		}
	}
}
