package com.zzj.homework.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class OpsForUid {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    public String getUid(String account){
        ServiceInstance serviceInstance = loadBalancerClient.choose("STUDENT"); //从Eureka服务器中找到该服务的地址等信息。
        String response = restTemplate.postForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/getUid",account,String.class);
        return response;
    }
}
