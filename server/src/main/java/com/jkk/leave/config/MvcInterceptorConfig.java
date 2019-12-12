package com.jkk.leave.config;

import com.jkk.leave.handler.CollegeApplyInterceptor;
import com.jkk.leave.handler.CounselorApplyInterceptor;
import com.jkk.leave.handler.StudentApplyInterceptor;
import com.jkk.leave.handler.TeacherApplyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
	private final StudentApplyInterceptor studentApplyInterceptor;
	private final CounselorApplyInterceptor counselorApplyInterceptor;
	private final CollegeApplyInterceptor collegeApplyInterceptor;
	private final TeacherApplyInterceptor teacherApplyInterceptor;
	public MvcInterceptorConfig(StudentApplyInterceptor studentApplyInterceptor, CounselorApplyInterceptor counselorApplyInterceptor, CollegeApplyInterceptor collegeApplyInterceptor, TeacherApplyInterceptor teacherApplyInterceptor) {
		this.studentApplyInterceptor = studentApplyInterceptor;
		this.counselorApplyInterceptor = counselorApplyInterceptor;
		this.collegeApplyInterceptor = collegeApplyInterceptor;
		this.teacherApplyInterceptor = teacherApplyInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(studentApplyInterceptor)
				.addPathPatterns("/stu/**");
		registry.addInterceptor(counselorApplyInterceptor)
				.addPathPatterns("/cou/**");
		registry.addInterceptor(collegeApplyInterceptor)
				.addPathPatterns("/col/**");
		registry.addInterceptor(teacherApplyInterceptor)
				.addPathPatterns("/tea/**");
	}
}
