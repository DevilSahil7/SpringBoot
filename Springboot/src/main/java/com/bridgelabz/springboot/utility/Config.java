package com.bridgelabz.springboot.utility;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class Config {

	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.springboot.controller"))
				.paths(regex("/api.*")).build().apiInfo(metaData());
	}

	public ApiInfo metaData() {
		@SuppressWarnings("deprecation")
		ApiInfo apiInfo = new ApiInfo("Spring Boot Rest Api",
				"Login and Registration Rest Api using Token Verification with JWT, JMS", "1.0", "Terms of service",
				"Please contact to chintookudake7@gmail.com", "Apache license Version 2.0",
				"https://www.apache.org/licenses/LICENSE-2.0");

		return apiInfo;
	}

}
