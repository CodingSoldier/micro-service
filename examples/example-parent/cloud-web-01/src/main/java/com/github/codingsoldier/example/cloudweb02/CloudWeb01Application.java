package com.github.codingsoldier.example.cloudweb02;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// @SpringBootApplication
// @EnableDiscoveryClient
// FeignClient在其他jar包中，需要加扫描路径
@EnableFeignClients("com.github.codingsoldier.example")
// @EnableCircuitBreaker 开启 hystrix
// @EnableCircuitBreaker
/**
 * @SpringCloudApplication 包含以下3个注解
 *
 * @SpringBootApplication
 * @EnableDiscoveryClient
 * @EnableCircuitBreaker
 */
@SpringCloudApplication
public class CloudWeb01Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudWeb01Application.class, args);
    }

}
