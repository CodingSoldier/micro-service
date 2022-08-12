package com.github.codingsoldier.example.bootweb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Tag(name = "日志测试-API")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogTestController {

    @GetMapping(value = "/test/print", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testPrint(String str) {

        log.debug("***** debug ***** {}", str);
        log.info("***** info ***** {}", str);
        log.warn("***** warn ***** {}", str);
        log.error("***** error ***** {}", str);

        log.error("异常", new RuntimeException(str));

        log.info("########## end ##########");
        return str;
    }

    @GetMapping(value = "/print/big", produces = MediaType.APPLICATION_JSON_VALUE)
    public String printBig(String str) throws Exception {
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