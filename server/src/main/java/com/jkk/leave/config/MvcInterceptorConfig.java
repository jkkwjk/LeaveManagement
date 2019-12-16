package com.jkk.leave.config;

import com.jkk.leave.handler.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
	private final StudentApplyInterceptor studentApplyInterceptor;
	private final CounselorApplyInterceptor counselorApplyInterceptor;
	private final CollegeApplyInterceptor collegeApplyInterceptor;
	private final TeacherApplyInterceptor teacherApplyInterceptor;
	private final LoginInterceptor loginInterceptor;
	public MvcInterceptorConfig(StudentApplyInterceptor studentApplyInterceptor, CounselorApplyInterceptor counselorApplyInterceptor, CollegeApplyInterceptor collegeApplyInterceptor, TeacherApplyInterceptor teacherApplyInterceptor, LoginInterceptor loginInterceptor) {
		this.studentApplyInterceptor = studentApplyInterceptor;
		this.counselorApplyInterceptor = counselorApplyInterceptor;
		this.collegeApplyInterceptor = collegeApplyInterceptor;
		this.teacherApplyInterceptor = teacherApplyInterceptor;
		this.loginInterceptor = loginInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/user/login")
				.excludePathPatterns("/img/**");

		registry.addInterceptor(studentApplyInterceptor)
				.addPathPatterns("/stu/**");
		registry.addInterceptor(counselorApplyInterceptor)
				.addPathPatterns("/cou/**");
		registry.addInterceptor(collegeApplyInterceptor)
				.addPathPatterns("/col/**");
		registry.addInterceptor(teacherApplyInterceptor)
				.addPathPatterns("/tea/**");
	}

	@Value("${prop.img}")
	private String IMG_FOLDER;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("file:" + IMG_FOLDER);
	}
}
