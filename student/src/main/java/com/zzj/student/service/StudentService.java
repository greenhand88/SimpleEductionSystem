package com.zzj.student.service;

import com.zzj.student.mappers.ClassMapper;

import org.springframework.amqp.core.AmqpTemplate;
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
    public ArrayList<String> getAllClass(){
        try {
            return classMapper.getAllClass();
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
