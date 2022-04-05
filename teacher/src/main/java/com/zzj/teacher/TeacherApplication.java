package com.zzj.teacher;

import com.zzj.teacher.VO.Login;
import com.zzj.teacher.VO.Result;
import com.zzj.teacher.entity.Account;
import com.zzj.teacher.service.AccountService;
import com.zzj.teacher.service.NotificationService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zzj.teacher.mappers")
@RestController
public class TeacherApplication {
    @Autowired
    AccountService accountService;
    @Autowired
    NotificationService notificationService;

    /**
     *
     * @param login
     * @return
     */
    @PostMapping("/login")
    public Result isPass(@RequestBody Login login) {
        try {
            return accountService.isPass(login.getAccount(), login.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(new String(), "500", false, "Exception!");
        }
    }

    /**
     *
     * @param account
     * @return
     */
    @PostMapping("/register")
    public Result registerAccount(@RequestBody Account account) {
        try {
            return accountService.registerStudentAccount(account.getAccount(), account.getPassword(),account.getUid(),account.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("", "500", false, "账号已存在!请重新注册!");
        }
    }
    @PostMapping("/notification")
    public Boolean notifiacation(@RequestParam("notification") String notification){
        if(notification==null)
            return false;
        try {
            return notificationService.addNotification(notification);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
