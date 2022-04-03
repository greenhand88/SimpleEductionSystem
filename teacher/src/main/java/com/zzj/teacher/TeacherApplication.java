package com.zzj.teacher;

import com.zzj.teacher.VO.Login;
import com.zzj.teacher.VO.Result;
import com.zzj.teacher.entity.Account;
import com.zzj.teacher.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class TeacherApplication {
    @Autowired
    AccountService accountService;

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
            return new Result("", "420", false, "账号已存在!请重新注册!");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
