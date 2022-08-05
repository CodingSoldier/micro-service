package com.github.codingsoldier.example.cloudweb01.controller;

import com.github.codingsoldier.common.util.CommonUtil;
import com.github.codingsoldier.example.cloudwebapi.CloudWeb02Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/web01")
public class Web01Controller {

    /**
     * 要使用动态刷新功能，必须在类上添加 @RefreshScope
     *
     * @NacosValue 注解竟然无法获取值
     */
    @Value("${test.val}")
    // @NacosValue()
    private String testVal;

    @Autowired
    private CloudWeb02Client cloudWeb02Client;

    @GetMapping("/test/get")
    public String testGet(String msg) {
        log.info("############aaaaaa");
        // String testVal = appApi.getTestVal(msg);
        // log.info("############{}", testVal);
        return "aaaa";
    }

    @GetMapping("/test/local/fegin")
    public String testLocalFein(String msg) {
        log.info("############{}  ", testVal);
        String result = cloudWeb02Client.getTestVal(msg
        );
        // String testVal = appApi.getTestVal(msg);
        log.info("############得到结果{}", result);
        return result;
    }


    @GetMapping("/testvoid")
    public String testvoid(String msg) {
        cloudWeb02Client.testvoid(msg);
        // String testVal = appApi.getTestVal(msg);
        log.info("############{}", msg);
        return msg;
    }

    @PostMapping("/test/namelist")
    public String namelist(@RequestBody(required = false) List<String> nameList) {
        String testVal = cloudWeb02Client.nameList(nameList);
        log.info("############{}", testVal);
        return testVal;
    }

    @PostMapping("/test/map")
    public String namelist(@RequestBody(required = false) Map map) {
        String testVal = cloudWeb02Client.map(map);
        log.info("############{}", testVal);
        return testVal;
    }

    @PostMapping("/test/bodyparam")
    public String bo(@RequestBody() Map map,
                     @RequestParam(value = "pageNum", required = false) Integer pageNum,
                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        String testVal = cloudWeb02Client.bodyAndParam(map, pageNum, pageSize);
        log.info("############{}", testVal);
        return testVal;
    }

    @DeleteMapping("/del")
    public String del(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("############{}", pageNum);
        log.info("############{}", pageSize);
        return pageSize.toString();
    }

    @GetMapping(value = "/test/val/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTestVal(HttpServletRequest request) {
        log.info("############{}", testVal);
        return testVal;
    }

    // response content type 是 text/plain
    @GetMapping(value = "/test/val")
    public String getTestValTextPlain(HttpServletRequest request) {
        log.info("############{}", testVal);
        return testVal;
    }

    @GetMapping(value = "/test/val/null")
    public String getTestValNull(HttpServletRequest request) {
        log.info("############{}", testVal);
        return null;
    }

    @PostMapping("/test/post/body")
    public Map postBoyd(@RequestBody(required = false) Map map) {
        log.info("############{}", map);
        return null;
    }

    @GetMapping("/test/big/body")
    public Map bigBody() {
        HashMap<Object, Object> map = new HashMap<>();
        log.info("############{}", map);
        for (int i = 0; i < 100; i++) {
            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                list.add(CommonUtil.uuid32());
            }
            map.put(CommonUtil.uuid32(), list);
        }
        return map;
    }

}