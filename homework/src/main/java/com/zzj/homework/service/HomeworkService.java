package com.zzj.homework.service;

import com.zzj.homework.VO.Mark;
import com.zzj.homework.tools.UploadHW;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HomeworkService {
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    UploadHW uploadHW;
    @Value("${mq.config.homework.exchange}")
    String hExchange;
    @Value("${mq.config.mark.exchange}")
    String mark;

    /**
     *
     * @param file
     * @param sid
     * @param cid
     * @return
     */
    public Boolean postHomework(MultipartFile file, String sid, String cid){
        try{
            uploadHW.saveFile(file,cid);
            amqpTemplate.convertAndSend(mark,new Mark(cid,sid));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //监听器
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.mark.queue}"),
            exchange = @Exchange("${mq.config.mark.exchange}")
    ))
    void sendToRedis(Mark mark){
        redisTemplate.opsForList().rightPush(mark.getCid(),mark.getSid());
    }
}
