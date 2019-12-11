package com.jkk.leave.web.api.v1;

import com.jkk.leave.service.LeaveApplyService;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys")
public class SysInfoController {
	private final LeaveApplyService leaveApplyService;

	public SysInfoController(LeaveApplyService leaveApplyService) {
		this.leaveApplyService = leaveApplyService;
	}

	@PostMapping("getTeam")
	public RestfulRes<List<String>> getAllTeam(){
		return RestfulRes.success(leaveApplyService.getTeam());
	}
}
