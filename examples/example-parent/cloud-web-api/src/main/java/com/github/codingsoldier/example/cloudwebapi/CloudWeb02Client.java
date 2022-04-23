package com.github.codingsoldier.example.cloudwebapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId= "cloudWeb02Client", path = "/cloud-web-02" + "/web02")
public interface CloudWeb02Client {

    @GetMapping("/cloud/test/val")
    String getTestVal(@RequestParam(value = "name", required = false) String name);

    @PostMapping("/list")
    String nameList(@RequestBody(required = false) List<String> nameList);

    @GetMapping("/testvoid")
    void testvoid(@RequestParam(value = "name", required = false) String name);

    @PostMapping("/map")
    String map(@RequestBody(required=false) @Nullable Map map);

    @PostMapping("/body/and/param")
    String bodyAndParam(@RequestBody Map map,
                    @RequestParam(value = "pageNum", required = false) Integer pageNum,
                    @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping("/testThreadPoolTraceId")
    String testThreadPoolTraceId(@RequestParam(value = "name", required = false) String name);

}
