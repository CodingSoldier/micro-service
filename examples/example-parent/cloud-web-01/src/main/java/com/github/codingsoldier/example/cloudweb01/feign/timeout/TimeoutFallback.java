package com.github.codingsoldier.example.cloudweb01.feign.timeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Sentinel 对 Web02Feign02Client 接口的降级策略
 */
@Slf4j
@Component
public class TimeoutFallback implements Web02FeignTimeoutClient {

    @Override
    public String timeout01(Long timeout) {
        return "调用web02出错，fallback降级处理。";
    }
}
