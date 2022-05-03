package com.zzj.teacher;

import com.zzj.teacher.VO.*;
import com.zzj.teacher.service.TeacherService;
import com.zzj.teacher.service.NotificationService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zzj.teacher.mappers")
@RestController
public class TeacherApplication {
    @Autowired
    TeacherService teacherService;
    @Autowired
    NotificationService notificationService;
    @PostMapping("/putHomework")
    public boolean putHomework(@RequestBody HInfor hInfor){
        return teacherService.putHomework(hInfor.getToken(),hInfor.getCid(),hInfor.getContent());
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getHomework")
    public ArrayList<Infor> getHomework(@RequestBody String token){
        return teacherService.getHomework(token);
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getCondition")
    public ArrayList<HCondition>getCondition(@RequestBody String token){
        return teacherService.getStudentCondition(token);
    }
    @PostMapping("/getName")
    public Teacher getName(@RequestBody String token){
        return new Teacher(teacherService.getName(token));
    }

    /**
     *
     * @param token
     * @return
     */
    @PostMapping("/getClassInfor")
    public ArrayList<ClassInfor>getClassInfor(@RequestBody String token){
        return teacherService.getClassInfor(token);
    }
    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
