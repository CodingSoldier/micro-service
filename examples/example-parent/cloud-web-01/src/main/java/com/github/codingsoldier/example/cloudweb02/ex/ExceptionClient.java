package com.github.codingsoldier.example.cloudweb02.ex;

import com.github.codingsoldier.example.cloudweb02.hystrix.HystrixClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fallbackFactory：可以获取 feign 调用异常
 * fallback：无法获取 feign 调用异常
 * @FeignClient 不能同时使用 fallbackFactory、fallback 两种属性
 */
@FeignClient(value = "cloud-web-02", contextId= "ex", path = "/cloud-web-02/exception"
, fallbackFactory = HystrixClientFallbackFactory.class)
public interface ExceptionClient {

    @GetMapping("/test01")
    String test01(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/appex")
    String appEx(@RequestParam(value = "name", required = false) String name);

}
