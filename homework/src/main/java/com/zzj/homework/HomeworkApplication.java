package com.zzj.homework;

import com.zzj.homework.service.HomeworkService;
import com.zzj.homework.tools.UploadHW;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@EnableEurekaClient
@RestController
@SpringBootApplication
@MapperScan("com.zzj.homework.mappers")
public class HomeworkApplication {
    @Autowired
    HomeworkService homeworkService;

    @PostMapping("/postHomeWork")
    public boolean postHomeWork(@RequestParam("file") MultipartFile file, @RequestParam("sid") String sid,@RequestParam("cid") String cid){
        return homeworkService.postHomework(file,sid,cid) ;
    }
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

}
