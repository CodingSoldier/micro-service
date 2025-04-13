package com.github.codingsoldier.example.cloudwebapi.web02;

import com.github.codingsoldier.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId = "web02FeignExceptionClient", path = "/cloud-web-02/feign02/exception")
public interface Web02FeignExceptionClient {

    @GetMapping(value = "/name")
    Map<String, Object> name(@RequestParam(value = "name") String name);

    @GetMapping(value = "/result/type/not/change")
    Result<String> resultTypeNotChange(@RequestParam(value = "name") String name);

    @GetMapping(value = "/timeout01", produces = MediaType.APPLICATION_JSON_VALUE)
    String timeout01(@RequestParam(value = "timeout") Long timeout);

}
