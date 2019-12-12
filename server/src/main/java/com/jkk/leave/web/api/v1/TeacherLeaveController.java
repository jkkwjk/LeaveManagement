package com.jkk.leave.web.api.v1;

import cn.hutool.core.date.DateUtil;
import com.jkk.leave.entity.POJO.Lesson;
import com.jkk.leave.entity.POJO.TeacherLeaveList;
import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.service.TeacherApplyService;
import com.jkk.leave.tools.TimeTool;
import com.jkk.leave.utils.RestfulRes;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tea")
public class TeacherLeaveController {
	private final TeacherApplyService teacherApplyService;

	public TeacherLeaveController(TeacherApplyService teacherApplyService) {
		this.teacherApplyService = teacherApplyService;
	}

	@PostMapping
	public RestfulRes<List<TeacherLeaveList>> getApplyList(@SessionAttribute("user")User user, int week, String lesson){
		String team = TimeTool.getThisTeam();
		week += TimeTool.getWeekOfYear(team, new Date().getTime());
		return RestfulRes.success(teacherApplyService.getApplyList(user,Integer.parseInt(team.split("-")[0]),week,lesson));
	}

	@GetMapping
	public RestfulRes<List<Lesson>> getLessonList(@SessionAttribute("user")User user){
		return RestfulRes.success(teacherApplyService.getLessons(user, TimeTool.getThisTeam()));
	}
}
