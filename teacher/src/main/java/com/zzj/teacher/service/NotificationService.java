package com.zzj.teacher.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NotificationService {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.notification.exchange}")
    private String exchange;
    //routingkey 路由键
    @Value("${mq.config.notification.routing.key}")
    private String routingkey;
    public Boolean addNotification(String notification) throws Exception{
            amqpTemplate.convertAndSend(exchange,routingkey,notification);
            return true;
    }
}
