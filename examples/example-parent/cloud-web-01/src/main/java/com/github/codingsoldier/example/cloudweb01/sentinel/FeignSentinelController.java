package com.github.codingsoldier.example.cloudweb01.sentinel;

import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feign/sentinel")
public class FeignSentinelController {

    @Autowired
    private FeignWeb02SentinelClient feignWeb02SentinelClient;

    /**
     * 下游服务使用了sentinel
     */
    @GetMapping("/from-nacos")
    public Result<String> fromNacos() {
        Result<String> resp = feignWeb02SentinelClient.fromNacos();
        log.info("成功调用下游sentinel熔断服务");
        return resp;
    }

}
