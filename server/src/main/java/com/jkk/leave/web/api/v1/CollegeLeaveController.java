package com.jkk.leave.web.api.v1;

import cn.hutool.core.date.DateUtil;
import com.jkk.leave.entity.POJO.ManageLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.POJO.ChartMap;
import com.jkk.leave.entity.VO.ChartNumVO;
import com.jkk.leave.service.CollegeApplyService;
import com.jkk.leave.tools.ExcelTool;
import com.jkk.leave.tools.FilterSorterParse;
import com.jkk.leave.tools.TimeTool;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
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

	@PostMapping("getArchive")
	public RestfulRes<List<ArchiveVO>> getArchive(Integer page, Integer num, Long startTime, Long endTime){
		return RestfulRes.success(collegeApplyService.getArchive(startTime, endTime, page, num));
	}

	@GetMapping("getArchive")
	public void downLoadArchive(Long startTime, Long endTime, HttpServletResponse response) throws UnsupportedEncodingException {
		List<ArchiveVO> archiveVOList = collegeApplyService.getArchive(startTime, endTime,null, null);
		if (archiveVOList.size() != 0){
			String fileName = "全校 "+ DateUtil.today()+" 的归档";
			ExcelTool.downLoad(response, fileName, archiveVOList);
		}
	}

	@PostMapping("getSummary")
	public RestfulRes<List<Integer>> getSummary(){
		List<Long[]> time = TimeTool.getOffsetTime(new Date());
		List<Integer> data = new ArrayList<>();
		data.add(collegeApplyService.getSummaryBetWeenTime(time.get(0)[0],time.get(0)[1]));// 今天
		data.add(collegeApplyService.getSummaryBetWeenTime(time.get(1)[0],time.get(1)[1]));// 昨天
		data.add(collegeApplyService.getSummaryBetWeenTime(time.get(2)[0],time.get(2)[1]));// 本周
		data.add(collegeApplyService.getSummaryBetWeenTime(time.get(3)[0],time.get(3)[1]));// 上周

		return RestfulRes.success(data);
	}

	@PostMapping("getMapChart")
	public RestfulRes<List<ChartMap>> getMapChart(String strTime){
		Long[] time = new Long[2];
		switch (strTime){
			case "month":
				time = TimeTool.getThisMonth();
				break;
			case "year":
				time = TimeTool.getThisYear();
				break;
			case "all":
				break;
			default:
				return RestfulRes.fail("获取请假地域信息错误");
		}
		return RestfulRes.success(collegeApplyService.getMapChartBetWeenTime(time[0], time[1]));
	}

	@PostMapping("getNumChart")
	public RestfulRes<List<ChartNumVO>> getNumChart(String strTime){
		Long[] time = new Long[2];
		switch (strTime){
			case "month":
				time = TimeTool.getThisMonth();
				break;
			case "year":
				time = TimeTool.getThisYear();
				break;
			case "all":
				break;
			default:
				return RestfulRes.fail("获取请假数量信息错误");
		}
		return RestfulRes.success(collegeApplyService.getNumChartBetWeenTime(time[0], time[1]));
	}
}
