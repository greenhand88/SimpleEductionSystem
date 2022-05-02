package com.zzj.teacher.service;

import com.zzj.teacher.mappers.TeacherMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${mq.config.notification.exchange}")
    private String exchange;
    //routingkey 路由键
    public Boolean addNotification(String notification) throws Exception{
            amqpTemplate.convertAndSend(exchange,notification);
            return true;
    }
    @RabbitListener(queues = "${mq.config.notification.queue}")
    public void saveNotification(String notification){
        redisTemplate.opsForList().rightPush("notifications",notification);
    }
}
