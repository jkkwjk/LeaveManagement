package com.jkk.leave.web.api.v1;

import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.service.CounselorApplyService;
import com.jkk.leave.tools.FilterSorterParse;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cou")
public class CounselorLeaveController {
	private final CounselorApplyService counselorApplyService;

	public CounselorLeaveController(CounselorApplyService counselorApplyService) {
		this.counselorApplyService = counselorApplyService;
	}

	@PostMapping
	public RestfulRes<List<ManageLeaveList>> getLeaveApply(@SessionAttribute("user") User user, Integer page, Integer num, String custom){
		Sorter sorter = FilterSorterParse.parseSorter(custom,"showWhat");
		Filter filter = FilterSorterParse.parseFilter(custom);

		return RestfulRes.success(counselorApplyService.getApply(user, page, num, filter, sorter));
	}

	@PostMapping("/{allow}/{id}")
	public RestfulRes addLeaveApply(@SessionAttribute("user") User user, @PathVariable String allow, @PathVariable String id){
		ManageLeaveList manageLeaveList = new ManageLeaveList();
		manageLeaveList.setId(Integer.parseInt(id));

		int num = 0;
		if (allow.equals("allow")){
			num = counselorApplyService.allowApply(manageLeaveList, user);
		}else if (allow.equals("reject")){
			num = counselorApplyService.rejectApply(manageLeaveList, user);
		}

		if (num == 1){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("请求失败");
		}
	}
}
