package com.zzj.homework.service;

import com.zzj.homework.tools.OpsForFilePath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    OpsForFilePath opsForFilePath;
    public String saveFile(MultipartFile file,String account) {
        if (file.isEmpty()) {
            return "文件为空!";
        }
        // 给文件重命名
        String fileName = file.getOriginalFilename()+ "." + file.getContentType()
                .substring(file.getContentType().lastIndexOf("/") + 1);
        try {
            // 获取保存路径
            String path = opsForFilePath.getSavePath(account);
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
