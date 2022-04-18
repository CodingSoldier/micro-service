package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.example.cloudweb02.service.Web02Service;
import com.github.codingsoldier.example.cloudwebapi.CloudWeb02Client;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "web02-API")
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/web02")
public class Web02Controller implements CloudWeb02Client {

    /**
     * 要使用动态刷新功能，必须在类上添加 @RefreshScope
     * @NacosValue 注解竟然无法获取值
     */
    @Value("${test.val:0000}")
    private String testVal;

    @Autowired
    Web02Service web02Service;

    @Override
    public String getTestVal(String name){
        // try {
        //     TimeUnit.SECONDS.sleep(10L);
        // } catch (Exception e){
        //     e.printStackTrace();
        // }
        web02Service.getTestVal("sdd");
        log.info("############{}", testVal);
        log.info("############{}", name);
        return testVal;
    }

    @Override
    public String nameList(@RequestBody(required = false) List<String> nameList) {
        log.info("##nameList {}", nameList);
        return "nameList";
    }

    @Override
    public void testvoid(String name) {
        log.info("########### {} " , name);
    }

    @Override
    public String map(@RequestBody(required = false) Map map) {
        log.info("##map {}", map);
        return "nameList";
    }

    @Override
    public String bodyAndParam(Map map, Integer pageNum, Integer pageSize) {
        log.info("#######map ", map.toString());
        log.info("#######pageNum ", pageNum);
        log.info("#######pageSize ", pageSize);
        return pageNum + "" + pageSize;
    }
    //
    // @GetMapping("/get/2")
    // public String getTestVal(String msg){
    //     log.info("############{}", msg);
    //     return msg;
    // }


}