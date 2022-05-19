package com.github.codingsoldier.example.cloudweb02.feign.timeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feign")
public class FeignTimeoutTestController {

    @Autowired
    private Web02FeignTimeoutClient web02FeignTimeoutClient;

    @GetMapping("/test01")
    public String test01(String msg) {
        log.info("############ {}", msg);
        String s = web02FeignTimeoutClient.test01(msg);
        log.info("@@@@@@@@@@@  {}", s);
        return s;
    }

}