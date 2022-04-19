package com.github.codingsoldier.example.cloudweb02.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hystrix")
public class HystrixTestController {

    @Autowired
    private HystrixClient hystrixClient;

    @GetMapping("/test01")
    public String test01(String msg){
        log.info("############ {}" , msg);
        String s = hystrixClient.test01(msg);
        log.info("@@@@@@@@@@@  {}" , s);
        return s;
    }

}