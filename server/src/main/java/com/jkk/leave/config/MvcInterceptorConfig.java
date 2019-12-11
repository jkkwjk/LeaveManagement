package com.jkk.leave.config;

import com.jkk.leave.handler.CounselorApplyInterceptor;
import com.jkk.leave.handler.StudentApplyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
	private final StudentApplyInterceptor studentApplyInterceptor;
	private final CounselorApplyInterceptor counselorApplyInterceptor;

	public MvcInterceptorConfig(StudentApplyInterceptor studentApplyInterceptor, CounselorApplyInterceptor counselorApplyInterceptor) {
		this.studentApplyInterceptor = studentApplyInterceptor;
		this.counselorApplyInterceptor = counselorApplyInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(studentApplyInterceptor)
				.addPathPatterns("/stu/**");
		registry.addInterceptor(counselorApplyInterceptor)
				.addPathPatterns("/cou/**");

	}
}
