package com.zzj.simpleeducationsysetm.service;

import com.zzj.simpleeducationsysetm.VO.Result;
import com.zzj.simpleeducationsysetm.mappers.AccountMapper;
import com.zzj.simpleeducationsysetm.tools.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private AccountMapper accountMapper;

    /**
     * @param account
     * @param password
     * @return token
     */
    public Result isPass(String account, String password) throws Exception {
        String s = accountMapper.getPassword(account);
        if (password.equals(s)) {
            String userName = accountMapper.getUserName(account);
            String token = Token.getToken(account, userName);
            redisTemplate.opsForValue().set(token, account, 60 * 30, TimeUnit.SECONDS);
            return new Result(token, "200", true, "登录成功!");
        } else
            return new Result(new String(), "300", false, "密码错误,请重新输入!");
    }

    /**
     * @param account
     * @param password
     * @return isSucceed
     */
    @Transactional
    public Result registerAccount(String account, String password, String userName) throws Exception {
        if (accountMapper.getUserName(account) == null || accountMapper.getUserName(account) == "")
            accountMapper.register(account, password, userName);
        else
            return new Result("", "360", false, "账号已存在!请重新注册!");
        return new Result("", "200", false, "注册成功!");
    }

    /**
     * @param account
     * @param newPassword
     * @return
     */
    @Transactional
    public Result changePassword(String account, String oldPassword, String newPassword) {
        if (accountMapper.getPassword(account).equals(oldPassword))
            accountMapper.changePassword(account, newPassword);
        else
            return new Result("", "415", false, "旧密码不正确,修改失败,请重新输入!");
        return new Result("", "200", false, "密码修改成功!");
    }

    /**
     * @param token
     * @return
     */
    public Result vertifyToken(String token) {
        if(token==null)
            return new Result(token, "350", false, "令牌失效,请重新登录!");
        if (redisTemplate.opsForValue().get(token) != "" && redisTemplate.opsForValue().get(token) != null)
            return new Result(token, "200", true, "免密码登录成功");
        else
            return new Result(token, "350", false, "令牌失效,请重新登录!");
    }

    /**
     *
     * @param token
     * @return
     */
    public Result getAccountByToken(String token) {
//        System.out.println(token);
//        System.out.println( redisTemplate.opsForValue().get(token));
        if(token==null)
            return new Result(token, "350", false, "令牌失效,请重新登录!");
        return new Result(token,"200",true,(String)redisTemplate.opsForValue().get(token));
    }

    /**
     *
     * @param token
     * @return
     */
    public Result getUserNameByToken(String token) {
//        System.out.println(token);
//        System.out.println( redisTemplate.opsForValue().get(token));
        if(token==null)
            return new Result(token, "350", false, "令牌失效,请重新登录!");
        return new Result(token,"200",true,accountMapper.getUserName((String)redisTemplate.opsForValue().get(token)));
    }

    /**
     *
     * @param token
     * @return
     */
    public Result signOut(String token){
        redisTemplate.delete(token);
        return new Result("","200",false,"登出成功");
    }

}
