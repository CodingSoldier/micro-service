package com.github.codingsoldier.example.cloudweb01.traceid;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 加上请求头 x-req-trace-id
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/trace")
public class TraceIdController {

    @Autowired
    private Web02TraceidClient web02TraceidClient;

    @GetMapping(value = "/testTraceId", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testTraceId(@RequestHeader Map<String, String> headers, String name) {
        return web02TraceidClient.testTraceId(name);
    }

    @GetMapping(value = "/asyncAnno", produces = MediaType.APPLICATION_JSON_VALUE)
    public String asyncAnno(@RequestHeader Map<String, String> headers, String name) {
        return web02TraceidClient.asyncAnno(name);
    }

    @GetMapping(value = "/throw/ex", produces = MediaType.APPLICATION_JSON_VALUE)
    public String throwEx(@RequestHeader Map<String, String> headers, String name) {
        web02TraceidClient.throwEx(name);
        return "aaa";
    }

    @GetMapping(value = "/get/mcd/req", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMcdReq(@RequestHeader Map<String, String> headers, String name) {
        String id = MDC.get("x-req-trace-id");
        log.info("###x-req-trace-id###{}", id);
        return "success";
    }


}