package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.example.cloudwebapi.CloudWeb02Client;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "web01-API")
@Slf4j
@RefreshScope
@RestController
@RequestMapping("/web01")
public class Web01Controller {

    /**
     * 要使用动态刷新功能，必须在类上添加 @RefreshScope
     * @NacosValue 注解竟然无法获取值
     */
    @Value("${test.val}")
    // @NacosValue()
    private String testVal;

    @Autowired
    private CloudWeb02Client cloudWeb02Client;

    @GetMapping("/test/get")
    public String testGet(String msg){
        log.info("############aaaaaa");
        // String testVal = appApi.getTestVal(msg);
        // log.info("############{}", testVal);
        return "aaaa";
    }

    @GetMapping("/test/local/fegin")
    public String testLocalFein(String msg){
        log.info("############{}  ", testVal);
        String result = cloudWeb02Client.getTestVal(msg
        );
        // String testVal = appApi.getTestVal(msg);
        log.info("############得到结果{}", result);
        return result;
    }


    @GetMapping("/testvoid")
    public String testvoid(String msg){
        cloudWeb02Client.testvoid(msg);
        // String testVal = appApi.getTestVal(msg);
        log.info("############{}", msg);
        return msg;
    }

    @PostMapping("/test/namelist")
    public String namelist(@RequestBody(required = false) List<String> nameList){
        String testVal = cloudWeb02Client.nameList(nameList);
        log.info("############{}", testVal);
        return testVal;
    }

    @PostMapping("/test/map")
    public String namelist(@RequestBody(required = false) Map map){
        String testVal = cloudWeb02Client.map(map);
        log.info("############{}", testVal);
        return testVal;
    }

    @PostMapping("/test/bodyparam")
    public String bo(@RequestBody() Map map,
                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                     @RequestParam(value = "pageSize", required = false) Integer pageSize){

        String testVal = cloudWeb02Client.bodyAndParam(map, pageNum, pageSize);
        log.info("############{}", testVal);
        return testVal;
    }

    @DeleteMapping("/del")
    public String del(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize){
        log.info("############{}", pageNum);
        log.info("############{}", pageSize);
        return pageSize.toString();
    }


    @GetMapping("/test/val")
    public String getTestVal(HttpServletRequest request){
        log.info("############{}", testVal);
        return testVal;
    }

}