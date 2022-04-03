package com.zzj.homework.service;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class UploadHW {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    public String getUid(String account){
        ServiceInstance serviceInstance = loadBalancerClient.choose("STUDENT"); //从Eureka服务器中找到该服务的地址等信息。
        String response = restTemplate.postForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/getUid",account,String.class);
        return response;
    }
    public String getSavePath(String account) {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());

        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\"+getUid(account)+"\\";
    }
    public String saveFile(MultipartFile file,String account) {
        if (file.isEmpty()) {
            return "文件为空!";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath(account);
            File files = new File(path, fileName);
            File parentFile = files.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdir();
            }
            file.transferTo(files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName; // 返回重命名后的文件名
    }
}
