package com.zzj.student;
import com.zzj.student.VO.ClassInfor;
import com.zzj.student.VO.Homework;
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

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getMyClass")
    public ArrayList<String> getmyClass(@RequestBody String token){
        return studentService.getMyClass(token);
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getMyTeacher")
    public ArrayList<ClassInfor>getClassInfor(@RequestBody String token){
        return studentService.getMyClassInfor(token);
    }
    @PostMapping("/getHomeWork")
    public ArrayList<Homework>getHomeWork(@RequestBody String token){
        return studentService.getHomework(token);
    }
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

}
