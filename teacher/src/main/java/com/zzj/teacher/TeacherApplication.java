package com.zzj.teacher;

import com.zzj.teacher.VO.HInfor;
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

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
