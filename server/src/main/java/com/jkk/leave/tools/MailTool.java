package com.jkk.leave.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.mail.MailUtil;
import com.jkk.leave.entity.DO.LeaveApplyDO;

import java.util.Date;

public final class MailTool {
	public static int sendEmail(String eMail, LeaveApplyDO leaveApplyDO){
		try {
			Thread thread = new Thread(() -> MailUtil.send(eMail, "有新的请假需要审批",
					String.format("<ul><li>学号: %d</li><li>类型: %s</li><li>原因: %s</li><li>开始时间: %s</li><li>结束时间: %s</li></ul>",
							leaveApplyDO.getStudentId(),leaveApplyDO.getType(),leaveApplyDO.getDetail(),
							DateUtil.format(new Date(leaveApplyDO.getStartTime()),"yyyy-MM-dd HH:mm:ss"),
							DateUtil.format(new Date(leaveApplyDO.getEndTime()),"yyyy-MM-dd HH:mm:ss")),
					true));
			thread.start();

		}catch (Exception e){
			return 0;
		}
		return 1;
	}
}
