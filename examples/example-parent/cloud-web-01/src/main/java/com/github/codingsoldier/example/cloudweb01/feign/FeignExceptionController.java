package com.github.codingsoldier.example.cloudweb01.feign;

import com.github.codingsoldier.example.cloudweb01.feign.exception.Web02FeignExceptionClient;
import com.github.codingsoldier.example.cloudweb01.feign.exception.Web02FeignExceptionFallbackClient;
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
@RequestMapping("/feign01/exception")
public class FeignExceptionController {

    @Autowired
    private Web02FeignExceptionClient web02FeignExceptionClient;
    @Autowired
    private Web02FeignExceptionFallbackClient web02FeignExceptionFallbackClient;

    @GetMapping(value = "/name")
    public Map<String, Object> name(@RequestParam(value = "name") String name) throws Exception {
        /**
         * feign客户端不加fallback
         */
        return web02FeignExceptionClient.name(name);
    }

    @GetMapping(value = "/has/fallback/name")
    public Map<String, Object> hasFallbackName(@RequestParam(value = "name") String name) throws Exception {
        /**
         * feign客户端加fallback
         */
        return web02FeignExceptionFallbackClient.name(name);
    }

}