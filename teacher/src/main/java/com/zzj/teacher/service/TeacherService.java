package com.zzj.teacher.service;

import com.zzj.teacher.VO.TClassInfor;
import com.zzj.teacher.mappers.TeacherMapper;
import com.zzj.teacher.tools.ProcessJson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TeacherService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.homework.exchange}")
    String exchange;
    public boolean putHomework(String token,String cid,String content){
        try{
            token= ProcessJson.processJson(token);
            cid=ProcessJson.processJson(cid);
            if (!(boolean) redisTemplate.opsForHash().get(token, "isTeacher"))
                return false;
            String tid=(String) redisTemplate.opsForHash().get(token,"uid");
            Random random=new Random();
            String hid=tid+cid+String.valueOf(random.nextInt(10000)) ;
            amqpTemplate.convertAndSend(exchange,new TClassInfor(tid,cid,hid,content));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //下面是监听器
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.homework.queue}"),
            exchange = @Exchange("${mq.config.homework.exchange}")
    ))
    void sendToRedis(TClassInfor tClassInfor){
        teacherMapper.putHomework(tClassInfor.getTid(),tClassInfor.getCid(),tClassInfor.getHid(),tClassInfor.getContent());
    }
}
