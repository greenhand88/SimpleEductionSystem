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
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
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
     * @param token
     * @param hid
     * @return
     */
    public Boolean postHomework(MultipartFile file, String token, String hid){
        synchronized (redisTemplate) {
            try{
                changeRedisDB(1);
                String sid = (String) redisTemplate.opsForHash().get(token, "uid");
                uploadHW.saveFile(file, hid);
                amqpTemplate.convertAndSend(mark, new Mark(hid, sid));
                changeRedisDB(2);
                return true;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
    /**
     *
     * @param index
     */
    private synchronized void changeRedisDB(int index){
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        //切换到指定的数据上
        connectionFactory.setDatabase(index);
        redisTemplate.setConnectionFactory(connectionFactory);
        //刷新配置
        connectionFactory.afterPropertiesSet();
        //重置连接
        connectionFactory.resetConnection();
    }
    //监听器
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.mark.queue}"),
            exchange = @Exchange("${mq.config.mark.exchange}")
    ))
    void sendToRedis(Mark mark){
        redisTemplate.opsForList().rightPush(mark.getHid(),mark.getSid());
    }
}
