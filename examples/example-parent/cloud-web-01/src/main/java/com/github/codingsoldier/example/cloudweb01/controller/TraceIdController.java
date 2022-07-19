package com.github.codingsoldier.example.cloudweb01.controller;

import com.github.codingsoldier.example.cloudwebapi.CloudWeb02Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}