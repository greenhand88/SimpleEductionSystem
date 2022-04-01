package com.zzj.login.controller;

import com.zzj.login.VO.ChangePassword;
import com.zzj.login.VO.Login;
import com.zzj.login.VO.Result;
import com.zzj.login.VO.TokenPermission;
import com.zzj.login.entity.Account;
import com.zzj.login.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("api/account/")
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
    @PostMapping("token")
    @ResponseBody
    public Result vertifyToken(@RequestBody TokenPermission tokenPermission) {
        return accountService.vertifyToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("getAccount")
    @ResponseBody
    public Result getAccount(@RequestBody TokenPermission tokenPermission) {
        return accountService.getAccountByToken(tokenPermission.getToken());
    }

    /**
     *
     * @param tokenPermission
     * @return
     */
    @PostMapping("getUserName")
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
    @PostMapping("signOut")
    @ResponseBody
    public Result signOut(@RequestBody TokenPermission tokenPermission) {
        return accountService.signOut(tokenPermission.getToken());
    }

}
