package com.jkk.leave.web.api.v1;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.POJO.base.Filter;
import com.jkk.leave.entity.POJO.base.Sorter;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.entity.VO.ArchiveVO;
import com.jkk.leave.entity.VO.WaitStatusVO;
import com.jkk.leave.service.LeaveApplyService;
import com.jkk.leave.tools.DataTool;
import com.jkk.leave.tools.ExcelTool;
import com.jkk.leave.tools.FilterSorterParse;
import com.jkk.leave.utils.RestfulRes;
import com.jkk.leave.tools.TimeTool;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		if (TimeTool.effectiveTime(leaveApplyVO.getStartTime(), leaveApplyVO.getEndTime()) && DataTool.effectiveType(leaveApplyVO.getType())){
			int id = leaveApplyService.addLeaveApply(leaveApplyVO,user);
			if (id != 0){
				return RestfulRes.success(leaveApplyService.getApplyById(id,user));
			}else {
				return RestfulRes.fail("添加失败");
			}
		}else {
			return RestfulRes.fail("字段无效");
		}

	}

	@PostMapping("modify")
	public RestfulRes modifyLeaveApply(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		if (TimeTool.effectiveTime(leaveApplyVO.getStartTime(), leaveApplyVO.getEndTime()) && DataTool.effectiveType(leaveApplyVO.getType())){
			if (leaveApplyService.modifyApply(leaveApplyVO, user) != 0){
				return RestfulRes.success();
			}else {
				return RestfulRes.fail("修改失败");
			}
		}else {
			return RestfulRes.fail("字段无效");
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

	@PostMapping("getStatus")
	public RestfulRes<WaitStatusVO> getLeaveApplyStatus(LeaveApplyVO leaveApplyVO, @SessionAttribute("user")User user){
		return RestfulRes.success(leaveApplyService.getLeaveStatus(leaveApplyVO.getId(), user));
	}

	@PostMapping("getArchive")
	public RestfulRes<List<ArchiveVO>> getArchive(Integer page, Integer num, Long startTime, Long endTime, @SessionAttribute("user")User user){
		return RestfulRes.success(leaveApplyService.getArchive(startTime, endTime, user, page, num));
	}

	@GetMapping("getArchive")
	public void downLoadArchive(Long startTime, Long endTime, @SessionAttribute("user")User user, HttpServletResponse response) throws UnsupportedEncodingException {
		List<ArchiveVO> archiveVOList = leaveApplyService.getArchive(startTime, endTime, user, null, null);
		if (archiveVOList.size() != 0){
			String fileName = archiveVOList.get(0).getStudentName()+" "+ DateUtil.today()+" 的归档";
			ExcelTool.downLoad(response, fileName, archiveVOList);
		}

	}
}
