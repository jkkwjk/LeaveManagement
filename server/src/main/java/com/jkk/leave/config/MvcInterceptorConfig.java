package com.jkk.leave.config;

import com.jkk.leave.handler.CollegeApplyInterceptor;
import com.jkk.leave.handler.CounselorApplyInterceptor;
import com.jkk.leave.handler.StudentApplyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
	private final StudentApplyInterceptor studentApplyInterceptor;
	private final CounselorApplyInterceptor counselorApplyInterceptor;
	private final CollegeApplyInterceptor collegeApplyInterceptor;
	public MvcInterceptorConfig(StudentApplyInterceptor studentApplyInterceptor, CounselorApplyInterceptor counselorApplyInterceptor, CollegeApplyInterceptor collegeApplyInterceptor) {
		this.studentApplyInterceptor = studentApplyInterceptor;
		this.counselorApplyInterceptor = counselorApplyInterceptor;
		this.collegeApplyInterceptor = collegeApplyInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(studentApplyInterceptor)
				.addPathPatterns("/stu/**");
		registry.addInterceptor(counselorApplyInterceptor)
				.addPathPatterns("/cou/**");
		registry.addInterceptor(collegeApplyInterceptor)
				.addPathPatterns("/col/**");
	}
}
