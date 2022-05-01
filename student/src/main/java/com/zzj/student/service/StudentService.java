package com.zzj.student.service;

import com.zzj.student.VO.ClassInfor;
import com.zzj.student.VO.Homework;
import com.zzj.student.mappers.ClassMapper;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     *
     * @return
     */
    public ArrayList<String> getAllClass(){
        try {
            return classMapper.getAllClass();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public ArrayList<String> getMyClass(String token){
        try {
            int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
            String s = token.substring(start, end);
            s=redisTemplate.opsForHash().get(s,"uid").toString();
            return classMapper.getMyClass(s);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public ArrayList<ClassInfor> getMyClassInfor(String token){
        try {
            int start=token.indexOf("\"",token.indexOf(":"))+1,end=token.indexOf("\"",start+1);
            String s = token.substring(start, end);
            s=redisTemplate.opsForHash().get(s,"uid").toString();
            return classMapper.getMyTeacher(s);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<ClassInfor>();
        }
    }
    public ArrayList<Homework> getHomework(String token){
        try{
            ArrayList<ClassInfor> list = getMyClassInfor(token);
            ArrayList<Homework> result = new ArrayList<>();
            for (ClassInfor i : list) {
                ArrayList<String>homework=classMapper.getHomework(i.getTid());
                result.add(new Homework(i,homework));
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Homework>();
        }
    }
}
