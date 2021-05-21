package com.anjanda.letsmeet;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.anjanda.letsmeet.user.jwt.JwtInterceptor;

/**
 * 
 * @Date : 2021. 1. 26.
 * @Team : AnJanDa
 * @author : 김동빈, 김지현, 임호빈
 * @deploy : 김동빈
 * @Project : Test - init 및 jwt 설치
 * @Function : Backend Boot run
 * @Description :
 * 	- 시작 시, 로그인과 관련된 jwt 토큰 설정하기 위한 객체 불러오기 + 시작
 * 	- 토큰 접속 관련 처리
 *	
 */

@PropertySource("classpath:/config.properties")
@SpringBootApplication
@MapperScan(basePackages ="com.anjanda.letsmeet.repository.mapper")
public class BackendApplication implements WebMvcConfigurer {
	
	/* 메인 메소드 실행 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
	/* JWT인터셉터 객체 불러오기 */
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	/* JWT인터셉터 호출 => 인터셉터 적용할 경로와 제외할 경로 설정 */
	// 토큰이 필요한 곳은 user 관련된 url입니다. 단, 로그인 시와 비밀번호 찾기할 땐 로그인 되어있지 않은 상태이므로, 토큰 요하는 인터셉터에서 제외
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/user/**")
												.excludePathPatterns(Arrays.asList("/login", "/user/forgot/**", "/user/join/**","/user/checkemail", "/user/mypage/**", "/user/find/**", "/user/delete/**"));
	}
	
	/* 인터셉터를 이용하여 로그인 처리하므로, 전역의 Cors Origin 처리 해준다. */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				.exposedHeaders("auth-token");
	}
}
