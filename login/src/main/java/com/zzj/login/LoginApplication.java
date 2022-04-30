package com.zzj.login;

import com.zzj.login.VO.Login;
import com.zzj.login.VO.Register;
import com.zzj.login.VO.Result;
import com.zzj.login.service.AccountService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableSwagger2
@EnableEurekaClient
@RestController
@SpringBootApplication
@MapperScan("com.zzj.login.mappers")
public class LoginApplication {
    @Autowired
    AccountService accountService;

    /**
     *
     * @param login
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody Login login){
        return accountService.pass(login);
    }

    /**
     *
     * @param register
     * @return
     */
    @PostMapping("/register")
    public Boolean register(@RequestBody Register register){
        return accountService.createAccount(register);
    }
    @PostMapping("/token")
    public Result loginByToken(@RequestBody String token){
        return accountService.loginByToken(token);
    }
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

}
