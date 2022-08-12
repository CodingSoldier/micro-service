package com.github.codingsoldier.example.cloudweb01.feign.timeout;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * fallbackFactory：可以获取 feign 调用异常，服务端未启动，会执行 fallbackFactory 的降级逻辑
 * fallback：无法获取 feign 调用异常，服务端未启动，不会执行 fallback 的降级逻辑
 *
 * @FeignClient 不能同时使用 fallbackFactory、fallback 两种属性
 * <p>
 * spring-cloud 2020 弃用 Hystrix
 * 可使用 sentinel 替代 hystrix
 */
// @FeignClient(value = "cloud-web-02", contextId= "hystrixClient", path = "/cloud-web-02/hystrix"
// , fallbackFactory = HystrixClientFallbackFactory.class)

// @FeignClient(value = "cloud-web-02", contextId= "web02Feign02Client", path = "/cloud-web-02/feign02/test", fallback= Sentinel02TimeoutFallback.class)

@FeignClient(value = "cloud-web-02", contextId = "web02FeignTimeoutClient", path = "/cloud-web-02/feign02/test", fallbackFactory = Sentinel02TimeoutFallbackFactory.class)
public interface Web02FeignTimeoutClient {

    @GetMapping(value = "/timeout01", produces = MediaType.APPLICATION_JSON_VALUE)
    String timeout01(@RequestParam(value = "timeout") Long timeout);

}
