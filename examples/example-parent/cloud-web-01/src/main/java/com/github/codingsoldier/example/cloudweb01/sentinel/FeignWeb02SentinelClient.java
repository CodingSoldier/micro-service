package com.github.codingsoldier.example.cloudweb01.sentinel;

import com.github.codingsoldier.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "cloud-web-02", contextId = "feignWeb02SentinelClient", path = "/cloud-web-02/sentinel")
public interface FeignWeb02SentinelClient {

    @GetMapping(value = "/from-nacos")
    Result<String> fromNacos();

}
