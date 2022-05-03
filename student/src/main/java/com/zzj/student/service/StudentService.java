package com.zzj.student.service;

import com.zzj.student.VO.ClassInfor;
import com.zzj.student.VO.Homework;
import com.zzj.student.VO.Infor;
import com.zzj.student.mappers.ClassMapper;

import com.zzj.student.tools.ProcessJson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class StudentService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
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
            String s = ProcessJson.processJson(token);
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
            String s = ProcessJson.processJson(token);
            s=redisTemplate.opsForHash().get(s,"uid").toString();
            return classMapper.getMyTeacher(s);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<ClassInfor>();
        }
    }

    /**
     *
     * @param token
     * @param hid
     * @return
     */
    public ArrayList<Homework> getHomework(String token,String hid){
        try{
            ArrayList<ClassInfor> list = getMyClassInfor(token);
            ArrayList<Homework> result = new ArrayList<>();
            for (ClassInfor i : list) {
                ArrayList<Infor>infors=classMapper.getHomework(i.getTid());
                result.add(new Homework(i,infors));
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Homework>();
        }
    }

    /**
     *
     * @param token
     * @return
     */
    public ArrayList<ArrayList<Infor>> getHInfor(String token){
        try{
            token=ProcessJson.processJson(token);
            ArrayList<ClassInfor> list = getMyClassInfor(token);
            ArrayList<ArrayList<Infor>>result=new ArrayList<>();
            for (ClassInfor i : list) {
                ArrayList<Infor>infors=classMapper.getHomework(i.getTid());
                result.add(infors);
            }
            return result;
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
    public String getName(String token){
        try{
            token = ProcessJson.processJson(token);
            return classMapper.getName((String)redisTemplate.opsForHash().get(token, "uid"));
        }catch (Exception e){
            e.printStackTrace();
            return new String();
        }
    }
}
