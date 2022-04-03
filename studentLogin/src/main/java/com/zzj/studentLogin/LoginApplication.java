package com.zzj.studentLogin;
import com.zzj.studentLogin.VO.ChangePassword;
import com.zzj.studentLogin.VO.Login;
import com.zzj.studentLogin.VO.Result;
import com.zzj.studentLogin.VO.TokenPermission;
import com.zzj.studentLogin.entity.StudentAccount;
import com.zzj.studentLogin.service.StudentAccountService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2

@SpringBootApplication
@EnableEurekaClient
@CrossOrigin
@MapperScan("com.zzj.studentLogin.mappers")
@RestController
public class LoginApplication {
    @Autowired
    StudentAccountService studentAccountService;
    /**
     * @param login
     * @return token
     */
    @PostMapping("/login")
    public Result isPass(@RequestBody Login login) {
        try {
            return studentAccountService.isPass(login.getAccount(), login.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(new String(), "404", false, "Exception!");
        }
    }

    /**
     *
     * @param studentAccount
     * @return
     */
    @PostMapping("/register")
    public Result registerAccount(@RequestBody StudentAccount studentAccount) {
        try {
            return studentAccountService.registerStudentAccount(studentAccount.getAccount(), studentAccount.getPassword(),studentAccount.getUid(),studentAccount.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("", "420", false, "账号已存在!请重新注册!");
        }
    }

    /**
     * @param changePassword
     * @return
     */
    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePassword changePassword) {
        try {
            return studentAccountService.changePassword(changePassword.getAccount(), changePassword.getOldPassword(), changePassword.getNewPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("", "404", false, "连接断开,密码修改失败!");
        }
    }
    @PostMapping("/getUid")
    public String getUid(@RequestBody String account){
        return studentAccountService.getUid(account);
    }

    /**
     * @param tokenPermission
     * @return
     */
    @PostMapping("/token")
    public Result vertifyToken(@RequestBody TokenPermission tokenPermission) {
        return studentAccountService.vertifyToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/getAccount")
    public Result getAccount(@RequestBody TokenPermission tokenPermission) {
        return studentAccountService.getAccountByToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/getUserName")
    public Result getUserName(@RequestBody TokenPermission tokenPermission) {
        return studentAccountService.getUserNameByToken(tokenPermission.getToken());
    }

    /**
     *
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/signOut")
    public Result signOut(@RequestBody TokenPermission tokenPermission) {
        return studentAccountService.signOut(tokenPermission.getToken());
    }

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }
}
