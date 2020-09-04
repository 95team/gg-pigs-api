package com.pangoapi.config.docs;

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

/**
 * [References]
 * 1. https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
 * 2. https://bamdule.tistory.com/36
 * 3. https://medium.com/@jinnyjinnyjinjin/java-spring-boot-swagger-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-4f83029bd57b
 * 4. https://victorydntmd.tistory.com/341
 * */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private String name = "Pango API";
    private String version;

    @Bean
    public Docket apiV1() {

        version = "v1";

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("com.pangoapi")
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pangoapi.controller"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(name)
                .version(version)
                .contact(new Contact("Contact us", "https://github.com/pigs-pango-team", "hjjae2@gmail.com"))
                .description("This page is for documentations of " + name + " " + version + " ")
                .build();
    }
}
