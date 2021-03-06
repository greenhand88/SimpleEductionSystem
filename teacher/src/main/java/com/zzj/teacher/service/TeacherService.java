package com.zzj.teacher.service;

import com.zzj.teacher.VO.*;
import com.zzj.teacher.mappers.TeacherMapper;
import com.zzj.teacher.tools.ProcessJson;
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

import java.util.ArrayList;
import java.util.List;
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
    public String getName(String token){
        token=ProcessJson.processJson(token);
        return teacherMapper.getName((String)redisTemplate.opsForHash().get(token, "uid"));
    }

    /**
     *
     * @param token
     * @param cid
     * @param content
     * @return
     */
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

    /**
     *
     * @param token
     * @return
     */
    public ArrayList<Infor>getHomework(String token){
        try{
            token = ProcessJson.processJson(token);
            if (!(boolean) redisTemplate.opsForHash().get(token, "isTeacher"))
                return new ArrayList<>();
            String tid = (String) redisTemplate.opsForHash().get(token, "uid");
            return teacherMapper.getHomework(tid);
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public ArrayList<HCondition>getStudentCondition(String token){
        try{
            token=ProcessJson.processJson(token);
            if (!(boolean) redisTemplate.opsForHash().get(token, "isTeacher"))
                return new ArrayList<>();
            String tid = (String) redisTemplate.opsForHash().get(token, "uid");
            changeRedisDB(2);
            ArrayList<Infor> homeworks = teacherMapper.getHomework(tid);
            ArrayList<HCondition>conditions=new ArrayList<>();
            for(Infor i:homeworks){
                conditions.add(new HCondition(i.getHid(),redisTemplate.opsForList().range(i.getHid(), 0, -1)));
            }
            changeRedisDB(1);
            return conditions;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param token
     * @param hid
     * @return
     */
    public ArrayList<Student>getSpecialCondition(String token,String hid){
        try{
            token=ProcessJson.processJson(token);
            if (!(boolean) redisTemplate.opsForHash().get(token, "isTeacher"))
                return new ArrayList<>();
            String tid = (String) redisTemplate.opsForHash().get(token, "uid");
            changeRedisDB(2);
            String uid = teacherMapper.getSpecialHInfor(tid,hid);
            List<Object> range = (ArrayList)redisTemplate.opsForList().range(uid, 0, -1);
            ArrayList<Student>conditions=new ArrayList<>();
            for(Object i:range){
                conditions.add(new Student(String.valueOf(i),teacherMapper.getSName(String.valueOf(i))));
            }
            changeRedisDB(1);
            return conditions;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public ArrayList<ClassInfor>getClassInfor(String token){
        token=ProcessJson.processJson(token);
        return teacherMapper.getClassInfor((String)redisTemplate.opsForHash().get(token, "uid"));
    }
    /**
     *
     * @param index
     */
    private synchronized void changeRedisDB(int index){
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
        //???????????????????????????
        connectionFactory.setDatabase(index);
        redisTemplate.setConnectionFactory(connectionFactory);
        //????????????
        connectionFactory.afterPropertiesSet();
        //????????????
        connectionFactory.resetConnection();
    }
    //??????????????????
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("${mq.config.homework.queue}"),
            exchange = @Exchange("${mq.config.homework.exchange}")
    ))
    void sendToRedis(TClassInfor tClassInfor){
        teacherMapper.putHomework(tClassInfor.getTid(),tClassInfor.getCid(),tClassInfor.getHid(),tClassInfor.getContent());
    }
}
