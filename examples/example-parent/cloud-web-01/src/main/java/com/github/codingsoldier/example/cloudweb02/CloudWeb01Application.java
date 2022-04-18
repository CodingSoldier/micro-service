package com.github.codingsoldier.example.cloudweb02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
/**
 * FeignClient在其他jar包中，需要加扫描路径
 */
@EnableFeignClients("com.github.codingsoldier.example")
public class CloudWeb01Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudWeb01Application.class, args);
    }

}
