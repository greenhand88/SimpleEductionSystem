package com.example.blog.controller;

import com.example.blog.VO.ChangePassword;
import com.example.blog.VO.Login;
import com.example.blog.VO.Result;
import com.example.blog.VO.TokenPermission;
import com.example.blog.entity.Account;
import com.example.blog.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/account")
@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    /**
     * @param login
     * @return token
     */
    @PostMapping("/login")
    @ResponseBody
    public Result isPass(@RequestBody Login login) {
        try {
            return accountService.isPass(login.getAccount(), login.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(new String(), "404", false, "Exception!");
        }
    }

    /**
     * @param account
     * @return isSucceed
     */
    @PostMapping("/register")
    @ResponseBody
    public Result registerAccount(@RequestBody Account account) {
        try {
            return accountService.registerAccount(account.getAccount(), account.getPassword(), account.getUserName());
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
    @ResponseBody
    public Result changePassword(@RequestBody ChangePassword changePassword) {
        try {
            return accountService.changePassword(changePassword.getAccount(), changePassword.getOldPassword(), changePassword.getNewPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("", "404", false, "连接断开,密码修改失败!");
        }
    }

    /**
     * @param tokenPermission
     * @return
     */
    @PostMapping("/api/token")
    @ResponseBody
    public Result vertifyToken(@RequestBody TokenPermission tokenPermission) {
        return accountService.vertifyToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/api/getAccount")
    @ResponseBody
    public Result getAccount(@RequestBody TokenPermission tokenPermission) {
        return accountService.getAccountByToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/api/getUserName")
    @ResponseBody
    public Result getUserName(@RequestBody TokenPermission tokenPermission) {
        return accountService.getUserNameByToken(tokenPermission.getToken());
    }

    /**
     *
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("/api/signOut")
    @ResponseBody
    public Result signOut(@RequestBody TokenPermission tokenPermission) {
        return accountService.signOut(tokenPermission.getToken());
    }

}
