package com.github.codingsoldier.bootweb.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "日志测试API")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogTestController {

    @GetMapping("/test")
    public String getTestMsg(String str) {
        log.debug("***** debug ***** {}", str);
        log.info("***** info ***** {}", str);
        log.warn("***** warn ***** {}", str);
        log.error("***** error ***** {}", str);


        log.error("异常", new RuntimeException(str));

        log.info("########## end ##########");
        return str;
    }

}