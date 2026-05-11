package com.github.codingsoldier.example.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class GatewayFallbackController {

    @RequestMapping("/fallback/{serviceName}")
    public void fallback(@PathVariable String serviceName) {
        log.error("gateway circuit breaker fallback, serviceName={}", serviceName);
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "服务暂不可用");
    }

}
