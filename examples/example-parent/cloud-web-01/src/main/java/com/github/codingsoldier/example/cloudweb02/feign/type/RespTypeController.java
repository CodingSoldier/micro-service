package com.github.codingsoldier.example.cloudweb02.feign.type;

import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/test/resp/type")
public class RespTypeController {

    @Autowired
    Web02ResultTypeClient web02ResultTypeClient;

    @GetMapping("/map")
    public Map map(@RequestParam(value = "name", required = false) String name) {
        Map map = web02ResultTypeClient.map(name);
        return map;
    }

    @GetMapping("/map/error")
    public Map mapError(@RequestParam(value = "name", required = false) String name) {
        Map map = null;
        try {
            map = web02ResultTypeClient.mapError(name);
        } catch (Exception e) {
            log.error("远程调用异常", e);
        }

        return map;
    }

    @GetMapping("/void")
    public void testVoid(@RequestParam(value = "name", required = false) String name) {
        web02ResultTypeClient.testVoid(name);
    }

    @GetMapping("/string")
    public String string(@RequestParam(value = "name", required = false) String name) {
        String string = web02ResultTypeClient.string(name);
        return string;
    }

    @GetMapping("/result")
    public Result<Map<String, String>> resultMap(@RequestParam(value = "name", required = false) String name) {
        Result<Map<String, String>> mapResult = web02ResultTypeClient.resultMap(name);
        return mapResult;
    }

}