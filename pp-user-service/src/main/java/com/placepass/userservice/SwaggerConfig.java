package com.placepass.userservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket getDocket() {

		return new Docket(DocumentationType.SWAGGER_2).groupName("placepass-api").select()
				.apis(RequestHandlerSelectors.basePackage("com.placepass.userservice.controller"))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {

		return new ApiInfoBuilder().title("User Service").description("User Service API Documentation")
				.termsOfServiceUrl("https://www.placepass.com/terms")
				.contact(new Contact("Place Pass", "https://www.placepass.com/contact", "developer@placepass.com"))
				.license("Private License").licenseUrl("https://www.placepass.com/terms").version("1.0").build();
	}

}
