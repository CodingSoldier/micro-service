package com.github.codingsoldier.example.cloudweb01.nacos;

import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/test-nacos")
public class TestNacosController {

    /**
     * 要使用动态刷新功能，必须在类上添加 @RefreshScope
     *
     * @NacosValue 注解竟然无法获取值
     */
    @Value("${test-nacos:null}")
    // @NacosValue()
    private String testNacos;

    @GetMapping(value = "/val", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<String> testNacos() {
        log.info("##########{}", testNacos);
        return Result.success(testNacos);
    }

}