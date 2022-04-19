package com.github.codingsoldier.middlewares.hystric.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * <h1>hystrix dashboard 入口</h1>
 * 注解：@EnableHystrixDashboard 开启 Hystrix Dashboard
 * Hystrix Dashboard页面地址：127.0.0.1:10101/hystrix-dashboard/hystrix/
 *
 * 输入：http://localhost:8001/cloud-web-01/actuator/hystrix.stream
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardApplication.class, args);
    }
}
