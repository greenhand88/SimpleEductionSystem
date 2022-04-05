package com.zzj.homework.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class OpsForUid {
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Value("${mq.config.uid.exchange}")
    private String exchange;
    //routingkey 路由键
    @Value("${mq.config.uid.routing.key}")
    private String routingkey;
//    @Autowired
//    private RestTemplate restTemplate;
    public String getUid(String account){
        //ServiceInstance serviceInstance = loadBalancerClient.choose("STUDENT"); //从Eureka服务器中找到该服务的地址等信息。
        //String response = restTemplate.postForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/getUid",account,String.class);
        String response=this.amqpTemplate.convertSendAndReceive(this.exchange, this.routingkey,account).toString();
        return response;
    }
}
