package com.zzj.simpleeducationsysetm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("com.zzj.simpleeducationsysetm.dao.Mappers")
@SpringBootApplication
public class SimpleEducationSysetmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleEducationSysetmApplication.class, args);
    }

}
