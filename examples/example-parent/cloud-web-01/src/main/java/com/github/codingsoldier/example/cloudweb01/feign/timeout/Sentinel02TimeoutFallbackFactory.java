package com.github.codingsoldier.example.cloudweb01.feign.timeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * FallbackFactory模式：
 * 1、FallbackFactory<T> 泛型是 Feign 接口 Web02Feign02Client
 * 2、create方法中创建 Web02Feign02Client 实例，作为返回值返回
 */
@Slf4j
@Component
public class Sentinel02TimeoutFallbackFactory implements FallbackFactory<Web02FeignTimeoutClient> {

    @Override
    public Web02FeignTimeoutClient create(Throwable throwable) {
        log.error("feign调用异常，", throwable);
        return new Web02FeignTimeoutClient() {
            @Override
            public String test01(String name) {
                return "调用服务2出错，降级处理。";
            }
        };
    }
}
