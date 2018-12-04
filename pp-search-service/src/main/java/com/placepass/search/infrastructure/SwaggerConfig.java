package com.placepass.search.infrastructure;

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

    /**
     * Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple swagger groups i.e. same code
     * base multiple swagger resource listings.
     */
    @Bean
    public Docket api() {

        // list of all controllers
        // return new Docket(DocumentationType.SWAGGER_2)
        // .select()
        // .apis(RequestHandlerSelectors.any())
        // .paths(PathSelectors.any())
        // .build()
        // .apiInfo(apiInfo());

        // Filtering API
        return new Docket(DocumentationType.SWAGGER_2).groupName("placepass-api").select()
                .apis(RequestHandlerSelectors.basePackage("com.placepass.search.controller"))
                // can use any(), none(), regex(), or ant()
                // .paths(PathSelectors.ant("/products/*"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("Searches").description("")
                .termsOfServiceUrl("https://en.wikipedia.org/wiki/Terms_of_service")
                .contact(new Contact("Place Pass", "https://en.wikipedia.org/wiki/Terms_of_service",
                        "developer@placepass.com"))
                .license("Private License").licenseUrl("https://en.wikipedia.org/wiki/Terms_of_service").version("1.0")
                .build();
    }

}
