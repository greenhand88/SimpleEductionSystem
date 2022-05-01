package com.zzj.student;
import com.zzj.student.service.StudentService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
@CrossOrigin
@MapperScan("com.zzj.student.mappers")
@RestController
public class StudentApplication {
    @Autowired
    StudentService studentService;

    /**
     *
     * @return
     */
    @GetMapping("/getAllClass")
    public ArrayList<String> getAllClass(){
        return studentService.getAllClass();
    }
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
}
