package com.shoppingmall.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage("com.shoppingmall"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .pathMapping("/")
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("eGov Mirim LMS API Document")
                                .description("미림미디어랩 LMS 프로젝트 API 를 명세하는 문서")
                                .version("1.0.0")
                                .contact(
                                        new Contact(
                                                "정지현",
                                                "dinb1242@naver.com",
                                                ""
                                        )
                                ).build()
                );
    }

}
