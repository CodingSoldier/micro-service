package com.github.codingsoldier.example.cloudweb01.feign.exception;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId = "web02FeignExceptionClient", path = "/cloud-web-02/feign02/exception")
public interface Web02FeignExceptionClient {

    @GetMapping(value = "/name")
    Map<String, Object> name(@RequestParam(value = "name") String name);

}
