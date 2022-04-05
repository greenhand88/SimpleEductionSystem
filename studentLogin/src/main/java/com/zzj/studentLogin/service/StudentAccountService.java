package com.zzj.studentLogin.service;

import com.zzj.studentLogin.VO.Result;
import com.zzj.studentLogin.mappers.StudentAccountMapper;
import com.zzj.studentLogin.tools.Token;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

@Service
public class StudentAccountService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private StudentAccountMapper studentAccountMapper;

    /**
     * @param account
     * @param password
     * @return token
     */
    public Result isPass(String account, String password) throws Exception {
        String s = studentAccountMapper.getPassword(account);
        if (password.equals(s)) {
            String userName = studentAccountMapper.getUid(account);
            String token = Token.getToken(account, userName);
            redisTemplate.opsForValue().set(token, account, 60 * 30, TimeUnit.SECONDS);
            return new Result(token, "200", true, "登录成功!");
        } else
            return new Result(new String(), "300", false, "密码错误,请重新输入!");
    }

    /**
     * 获取学号
     * @param account
     * @return
     */
    public String getUid(String account){
        return studentAccountMapper.getUid(account);
    }

    /**
     * @param account
     * @param password
     * @return isSucceed
     */
    public Result registerStudentAccount(String account, String password, String uid,String name) throws Exception {
        if (studentAccountMapper.getUid(account) == null || studentAccountMapper.getUid(account) == "")
            studentAccountMapper.register(account, password, uid,name);
        else
            return new Result("", "505", false, "账号已存在!请重新注册!");
        return new Result("", "200", false, "注册成功!");
    }

    /**
     * @param account
     * @param newPassword
     * @return
     */
    @Transactional
    public Result changePassword(String account, String oldPassword, String newPassword) {
        if (studentAccountMapper.getPassword(account).equals(oldPassword))
            studentAccountMapper.changePassword(account, newPassword);
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
        return new Result(token,"200",true,studentAccountMapper.getUid((String)redisTemplate.opsForValue().get(token)));
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
