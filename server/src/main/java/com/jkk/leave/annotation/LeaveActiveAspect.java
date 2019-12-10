package com.jkk.leave.annotation;

import com.jkk.leave.entity.POJO.User;
import com.jkk.leave.entity.VO.LeaveApplyVO;
import com.jkk.leave.service.LeaveApplyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LeaveActiveAspect {
	private final LeaveApplyService leaveApplyService;

	public LeaveActiveAspect(LeaveApplyService leaveApplyService) {
		this.leaveApplyService = leaveApplyService;
	}

	@Pointcut("@annotation(com.jkk.leave.annotation.LeaveActive)")
	public void addAdvice(){}

	@Around("addAdvice()")
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		User user = null;
		LeaveApplyVO leaveApplyVO = null;
		for (Object arg : args) {
			if (arg instanceof User){
				user = (User) arg;
			}else if (arg instanceof LeaveApplyVO){
				leaveApplyVO = (LeaveApplyVO) arg;
			}
		}

		if (user != null && leaveApplyVO != null){
			if (!leaveApplyService.isSend(leaveApplyVO, user) && !leaveApplyService.isInTrashBin(leaveApplyVO, user)){
				return pjp.proceed(pjp.getArgs());
			}else {
				return 0;
			}
		}else {
			return 0;
		}

	}
}
