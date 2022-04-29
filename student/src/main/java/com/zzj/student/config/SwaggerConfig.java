package com.zzj.student.config;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.profiles.active:NA}")
    private String active;
    @Bean
    public Docket createRestApi() {//http://localhost:8760/swagger-ui.html
        return new Docket(DocumentationType.SWAGGER_2)  // OAS_30
                //.enable("dev".equals(active))  // 仅在开发环境开启Swagger
                .enable(true)//默认一直开启
                .apiInfo(apiInfo())
                .host("http://localhost:18000")  // Base URL
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zzj.studentLogin"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("这是描述信息")
                .contact(new Contact("张三", null, null))
                .version("1.0")
                .build();
    }
}
