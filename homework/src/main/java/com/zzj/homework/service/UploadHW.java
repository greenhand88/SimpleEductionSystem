package com.zzj.homework.service;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadHW {
    public static String getSavePath() {
        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
        // 获取项目下resources/static/img路径
        ApplicationHome applicationHome = new ApplicationHome(UploadHW.class);

        // 保存目录位置根据项目需求可随意更改
        return applicationHome.getDir().getParentFile()
                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\";
    }
    public static String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空!";
        }
        // 给文件重命名
        String fileName = UUID.randomUUID() + "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = getSavePath();
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
