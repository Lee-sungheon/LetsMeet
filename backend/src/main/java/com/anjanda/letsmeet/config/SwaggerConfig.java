package com.anjanda.letsmeet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @Date : 2021. 2. 1.
 * @Team : AnJanDa
 * @author : 김지현, 김동빈, 임호빈
 * @deploy : 김동빈
 * @Project : 레쓰밋 :: backend
 * @Function : REST API 방식으로 데이터 전송 잘 오고가는지 확인하기 위한 Swagger
 * @Description
 *	- http://localhost:8000/letsmeet/swagger-ui.html 해당 주소로 swagger 활용(백엔드 실행후...)
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("anjanda-Let's Meet")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("레쓰밋(Let's Meet)")
				.description("약속을 잡아주고 관리해주는 웹 앱 : 레쓰밋(Let's Meet)")
				.termsOfServiceUrl("https://www.notion.so/oct14jh/Let-s-Meet-8ad9360ca0684bd98b905fd383c9c30b")
				.license("안잔다 Team - 레쓰밋 프로젝트")
				.licenseUrl("khyun7621@naver.com").version("1.0").build();
	}
}