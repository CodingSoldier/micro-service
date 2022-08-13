package com.github.codingsoldier.example.cloudweb01.feign.exception;

import com.github.codingsoldier.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId = "web02FeignExceptionFallbackClient", path = "/cloud-web-02/feign02/exception", fallbackFactory = ExceptionFallbackFactory.class)
public interface Web02FeignExceptionFallbackClient {

    @GetMapping(value = "/name")
    Map<String, Object> name(@RequestParam(value = "name") String name);

    @GetMapping(value = "/result/type/not/change")
    Result resultTypeNotChange(@RequestParam(value = "name") String name);

}
