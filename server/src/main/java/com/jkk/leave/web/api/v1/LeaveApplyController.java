package com.jkk.leave.web.api.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.service.LeaveApplyService;
import com.jkk.leave.tools.ColumnMapTool;
import com.jkk.leave.tools.FilterSorterParse;
import com.jkk.leave.utils.RestfulRes;
import com.jkk.leave.tools.TimeTool;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stu")
public class LeaveApplyController {
	private final LeaveApplyService leaveApplyService;

	public LeaveApplyController(LeaveApplyService leaveApplyService) {
		this.leaveApplyService = leaveApplyService;
	}

	/**
	 * 得到请假数据
	 * @param user session中的用户名
	 * @param page 分页参数
	 * @param num 分页参数
	 * @param custom 自定义请求 sort与filter
	 */
	@PostMapping
	public RestfulRes<List<LeaveApplyVO>> getLeaveApply(@SessionAttribute("user")User user, Integer page, Integer num,String custom){
		Sorter sorter = FilterSorterParse.parseSorter(custom, "sendTime");
		Filter filter = FilterSorterParse.parseFilter(custom);

		return RestfulRes.success(leaveApplyService.getApplyList(user, page, num, filter, sorter));
	}

	/**
	 * 得到回收站中的请假数据
	 * @param user session中的用户名
	 */
	@GetMapping
	public RestfulRes<List<LeaveApplyVO>> getLeaveApplyInTrashBin(@SessionAttribute("user")User user){
		return RestfulRes.success(leaveApplyService.getApplyListInTrashBin(user));
	}

	/**
	 * 添加请假数据
	 * @param leaveApplyVO
	 * @param user session中的user
	 * @return 返回LeaveApplyVO
	 */
	@PostMapping("add")
	public RestfulRes<LeaveApplyVO> addLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (TimeTool.effectiveTime(leaveApplyVO.getStartTime(), leaveApplyVO.getEndTime())){
			int id = leaveApplyService.addLeaveApply(leaveApplyVO,user);
			if (id != 0){
				return RestfulRes.success(leaveApplyService.getApplyById(id,user));
			}else {
				return RestfulRes.fail("添加失败");
			}
		}else {
			return RestfulRes.fail("时间无效");
		}

	}

	@PostMapping("modify")
	public RestfulRes modifyLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (TimeTool.effectiveTime(leaveApplyVO.getStartTime(), leaveApplyVO.getEndTime())){
			if (leaveApplyService.modifyApply(leaveApplyVO, user) != 0){
				return RestfulRes.success();
			}else {
				return RestfulRes.fail("修改失败");
			}
		}else {
			return RestfulRes.fail("时间无效");
		}

	}

	@PostMapping("toTrash")
	public RestfulRes toTrashLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (leaveApplyService.apply2TrashBin(leaveApplyVO, user) != 0){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("放入回收站失败");
		}
	}

	@PostMapping("send")
	public RestfulRes sendLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		leaveApplyVO = leaveApplyService.getApplyById(leaveApplyVO.getId(), user);

		if (TimeTool.effectiveTime(leaveApplyVO.getStartTime(), leaveApplyVO.getEndTime())){
			if (leaveApplyService.sendApply(leaveApplyVO, user) != 0){
				return RestfulRes.success();
			}else {
				return RestfulRes.fail("发送失败");
			}
		}else {
			return RestfulRes.fail("时间无效");
		}

	}

	@PostMapping("del")
	public RestfulRes delLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (leaveApplyService.deleteApply(leaveApplyVO, user) != 0){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("删除失败");
		}
	}

	@PostMapping("reduction")
	public RestfulRes reductionLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (leaveApplyService.reductionApply(leaveApplyVO, user) != 0){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("还原失败");
		}
	}
}
