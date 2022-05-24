package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.common.util.StringUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(tags = "日志测试API")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogTestController {

    @GetMapping("/test/msg")
    public String getTestMsg(String str) {

        log.debug("***** debug ***** {}", str);
        log.info("***** info ***** {}", str);
        log.warn("***** warn ***** {}", str);
        log.error("***** error ***** {}", str);


        log.error("异常", new RuntimeException(str));

        log.info("########## end ##########");
        return str;
    }

    @GetMapping("/test/while")
    public String testWhile(String str) throws Exception {
        while (true) {
            log.debug("***** debug ***** {}", str);
            log.info("***** info ***** {}", str);
            log.warn("***** warn ***** {}", str);
            log.error("***** error ***** {}", str);


            log.error("异常", new RuntimeException(str));

            log.info("########## end ##########");

            TimeUnit.NANOSECONDS.sleep(10L);
            if (StringUtils.equals("break", str)) {
                break;
            }
        }
        return str;
    }

}