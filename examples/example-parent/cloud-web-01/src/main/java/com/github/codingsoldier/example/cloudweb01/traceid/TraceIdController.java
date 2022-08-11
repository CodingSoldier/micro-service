package com.github.codingsoldier.example.cloudweb01.traceid;

import com.github.codingsoldier.example.cloudwebapi.CloudWeb02Client;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.WebFluxSleuthOperators;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.util.context.Context;

import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/trace")
public class TraceIdController {

    @Autowired
    private CloudWeb02Client cloudWeb02Client;

    @GetMapping(value = "/testThreadPoolTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testThreadPoolTraceId(@RequestHeader Map<String, String> headers, String name) {
        log.info(String.format("########打印请求头 %s", headers.toString()));
        log.info("############testThreadPoolTraceId");
        String s = cloudWeb02Client.testThreadPoolTraceId(name);
        return s;
    }

    @GetMapping(value = "/poolTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String poolTraceId(@RequestHeader Map<String, String> headers, String name) {

        Tracer tracer = ApplicationContextHolder.getBean(Tracer.class);
        Context currentTraceContext = ApplicationContextHolder.getBean(Context.class);
        // currentTraceContext.
        // 打印日志
        WebFluxSleuthOperators.withSpanInScope(currentTraceContext, () -> log.info("######线程中打印日志###########"));


        // ThreadUtil.execute(() -> {
        //     log.info("#########################");
        //     WebFluxSleuthOperators.withSpanInScope(() -> log.info("######线程中打印日志###########"));
        // });
        return "SSSSS";
    }

}