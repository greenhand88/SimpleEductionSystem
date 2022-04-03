package com.zzj.homework;

import com.zzj.homework.service.UploadHW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;


@EnableEurekaClient
@RestController
@SpringBootApplication
public class HomeworkApplication {
    @Autowired
    UploadHW uploadHW;
    @PostMapping("/upload")
    public String upLoadFile(@RequestParam("file") MultipartFile file,@RequestParam("account") String account){
        return uploadHW.saveFile(file,account);
    }
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

}
