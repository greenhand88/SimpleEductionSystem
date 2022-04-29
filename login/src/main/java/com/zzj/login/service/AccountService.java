package com.zzj.login.service;

import com.zzj.login.VO.Id;
import com.zzj.login.VO.Login;
import com.zzj.login.VO.Result;
import com.zzj.login.mappers.AccountMapper;
import com.zzj.login.tool.Token;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    //下面是监听器
    @RabbitListener(queues = "${mq.config.login.queue}")
    public void sendToRedis(Result result){
        redisTemplate.opsForHash().put(result.getToken(),"uid",result.getUid());
        redisTemplate.opsForHash().put(result.getToken(),"isTeacher",result.getIsTeacher());
    }
}
