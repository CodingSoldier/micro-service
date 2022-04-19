package com.github.codingsoldier.example.cloudweb02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @GetMapping("/test01")
    public String test01(@RequestParam(value = "name", required = false) String name) {
        // try {
        //     TimeUnit.SECONDS.sleep(10L);
        // } catch (Exception e){
        //     e.printStackTrace();
        // }
        log.info("############{}", name);
        return name+name;
    }



}