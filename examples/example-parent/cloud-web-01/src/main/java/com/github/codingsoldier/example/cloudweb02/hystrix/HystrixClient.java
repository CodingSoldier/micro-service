package com.github.codingsoldier.example.cloudweb02.hystrix;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fallbackFactory：可以获取 feign 调用异常
 * fallback：无法获取 feign 调用异常
 * @FeignClient 不能同时使用 fallbackFactory、fallback 两种属性
 */
// @FeignClient(value = "cloud-web-02", contextId= "hystrixClient", path = "/cloud-web-02/hystrix"
// , fallbackFactory = HystrixClientFallbackFactory.class)
@FeignClient(value = "cloud-web-02", contextId= "hystrixClient", path = "/cloud-web-02/hystrix")
public interface HystrixClient {

    @GetMapping("/test01")
    String test01(@RequestParam(value = "name", required = false) String name);

}
