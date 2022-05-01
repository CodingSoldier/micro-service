package com.github.codingsoldier.example.cloudweb02.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Sentinel 对 Web02Feign02Client 接口的降级策略
 */
@Slf4j
@Component
public class Sentinel02Fallback implements Web02Feign02Client {

    @Override
    public String test01(String name) {
        return "sentinel对test01(String name)降级。";
    }
}
