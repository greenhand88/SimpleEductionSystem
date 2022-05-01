package com.zzj.login.service;

import com.zzj.login.VO.Id;
import com.zzj.login.VO.Login;
import com.zzj.login.VO.Register;
import com.zzj.login.VO.Result;
import com.zzj.login.mappers.AccountMapper;
import com.zzj.login.tool.Token;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${mq.config.login.exchange}")
    String exchange;
    @Value("${mq.config.login.routing.key}")
    String routeKey;
    @Value("${mq.config.signOut.routing.key}")
    String signOutKey;

    /**
     *
     * @param login
     * @return
     */
    public Result pass(Login login){
        Id id=accountMapper.getInfor(login.getAccount());
        if(id!=null&&id.getPassword().equals(login.getPassword())){
            String token= Token.getToken(login.getAccount());
            Result result=new Result(token,id.getUid(),id.getIsT()==1,true);
            amqpTemplate.convertAndSend(exchange,routeKey,result);
            return result;
        }
        return new Result("","",false,false);
    }

    /**
     *
     * @param register
     * @return
     */
    public Boolean createAccount(Register register){
        try{//数据库已经设置unique了
            accountMapper.register(register.getAccount(),register.getPassword(),register.getIsT(),register.getUid());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public Result loginByToken(String token){
        try{
            int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
            String s = token.substring(start, end);
            String uid = redisTemplate.opsForHash().get(s, "uid").toString();
            boolean isTeacher=(boolean) redisTemplate.opsForHash().get(s, "isTeacher");
            return new Result(s,uid,isTeacher,true);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("","",false,false);
        }
    }
    public Boolean signOut(String token){
        try{
            int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
            String s = token.substring(start, end);
            amqpTemplate.convertAndSend(exchange,signOutKey,s);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //下面是监听器
    @RabbitListener(queues = "${mq.config.login.queue}")
    public void sendToRedis(Result result){
        redisTemplate.opsForHash().put(result.getToken(),"uid",result.getUid());
        redisTemplate.opsForHash().put(result.getToken(),"isTeacher",result.getIsTeacher());
        redisTemplate.expire(result.getToken(),30, TimeUnit.MINUTES);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.signOut.queue}"),
            exchange = @Exchange("${mq.config.login.exchange}"),
            key="${mq.config.signOut.routing.key}"))
    public void deleteInRedis(String token){
        redisTemplate.delete(token);
    }
}
