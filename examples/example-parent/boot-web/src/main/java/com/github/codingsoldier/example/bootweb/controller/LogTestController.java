package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.common.exception.ClientException;
import com.github.codingsoldier.starter.micrometer.tracing.config.TheadPoolTraceUtil;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Tag(name = "日志测试-API")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogTestController {

    @Autowired
    ThreadPoolTaskExecutor scheduler;

    @GetMapping(value = "/test/print", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testPrint(String str) {
        log.debug("***** debug ***** {}", str);
        log.info("***** info ***** {}", str);
        log.warn("***** warn ***** {}", str);
        log.error("***** error ***** {}", str);

        log.error("异常", new RuntimeException(str));

        TheadPoolTraceUtil.execute(() -> {
            log.info("@@@@@@ TheadPoolTraceUtil.execute 新线程中打印日志 @@@@@@@");
            throw new RuntimeException("异常TheadPoolTraceUtil.execute");
        });

        TheadPoolTraceUtil.submit(() -> {
            log.info("@@@@@@ TheadPoolTraceUtil.submit 新线程中打印日志 @@@@@@@");
            return 1;
        });

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

    @GetMapping(value = "/limit", produces = MediaType.APPLICATION_JSON_VALUE)
    public String printLimit(String p1) {

        String stackTrace = ExceptionUtils.getStackTrace(new RuntimeException("测试信息长度限制"));
        stackTrace = stackTrace.replaceAll("\r\n\t", "");
        log.info(stackTrace);
        ClientException ex = new ClientException("测试异常长度限制");
        if ("logEx".equals(p1)) {
            log.error("", ex);
        }
        return "success";
    }


}