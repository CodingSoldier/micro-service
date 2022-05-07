package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.common.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/test01")
    public String test01(@RequestParam(value = "name", required = false) String name) {
        if ("ex".equals(name)) {
            throw new RuntimeException("抛出异常");
        }
        log.info("############{}", name);
        return name + name;
    }

    @GetMapping("/appex")
    public String appEx(@RequestParam(value = "name", required = false) String name) {
        if ("ex".equals(name)) {
            throw new AppException("name不对");
        }
        log.info("############{}", name);
        return name + name;
    }


}