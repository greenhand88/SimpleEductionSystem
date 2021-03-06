package com.zzj.student;
import com.zzj.student.VO.*;
import com.zzj.student.service.StudentService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashMap;

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
    @PostMapping("/getClassInfor")
    public ArrayList<ClassInfor>getClassInfor(@RequestBody String token){
        return studentService.getMyClassInfor(token);
    }

    /**
     *
     * @param requestH
     * @return
     */
    @PostMapping("/getHomeWork")
    public ArrayList<Homework>getHomeWork(@RequestBody RequestH requestH){
        return studentService.getHomework(requestH.getToken(),requestH.getHid());
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getAllHInfor")
    public ArrayList<ArrayList<Infor>> getAllHInfor(@RequestBody String token){
        return studentService.getHInfor(token);
    }
    @PostMapping("/getSName")
    public Result getName(@RequestBody String token){
        return new Result(studentService.getName(token));
    }
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }

}
