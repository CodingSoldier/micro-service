package com.github.codingsoldier.example.cloudweb02.ex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/ex")
public class ExController {

    @Autowired
    private ExceptionClient exceptionClient;

    @GetMapping("/test01")
    public String testGet(String msg){
        String s = exceptionClient.test01(msg);
        log.info("############{}", s);
        return "aaaa"+s;
    }

    @GetMapping("/appex")
    public String appex(String msg){
        String s = exceptionClient.appEx(msg);
        log.info("############{}", s);
        return "aaaa"+s;
    }

}