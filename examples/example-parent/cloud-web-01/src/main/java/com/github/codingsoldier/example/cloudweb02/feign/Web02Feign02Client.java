package com.github.codingsoldier.example.cloudweb02.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fallbackFactory：可以获取 feign 调用异常，服务端未启动，会执行 fallbackFactory 的降级逻辑
 * fallback：无法获取 feign 调用异常，服务端未启动，不会执行 fallback 的降级逻辑
 * @FeignClient 不能同时使用 fallbackFactory、fallback 两种属性
 *
 * spring-cloud 2020 弃用 Hystrix
 * 可使用 sentinel 替代 hystrix
 *
 */
// @FeignClient(value = "cloud-web-02", contextId= "hystrixClient", path = "/cloud-web-02/hystrix"
// , fallbackFactory = HystrixClientFallbackFactory.class)

// @FeignClient(value = "cloud-web-02", contextId= "web02Feign02Client", path = "/cloud-web-02/feign02", fallback= Sentinel02Fallback.class)

@FeignClient(value = "cloud-web-02", contextId= "web02Feign02Client", path = "/cloud-web-02/feign02", fallbackFactory= Sentinel02FallbackFactory.class)
public interface Web02Feign02Client {

    @GetMapping("/test01")
    String test01(@RequestParam(value = "name", required = false) String name);

}
