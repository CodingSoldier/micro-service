package com.github.codingsoldier.example.cloudweb01.feign.type;

import com.github.codingsoldier.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId = "web02ResultTypeClient", path = "/cloud-web-02/feign02/resp/type")
public interface Web02ResultTypeClient {

    @GetMapping("/map")
    Map map(@RequestParam(value = "name") String name);

    @GetMapping("/map/error")
    Map mapError(@RequestParam(value = "name") String name);

    @GetMapping("/void")
    void testVoid(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/string")
    String string(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/demo-vo")
    DemoVo demoVo(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/result")
    Result<Map<String, String>> resultMap(@RequestParam(value = "name") String name);

}
