package com.github.codingsoldier.example.cloudwebapi;

import com.github.codingsoldier.common.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "cloud-web-02", contextId = "web02FeignTestClient", path = "/cloud-web-02/feign02/test")
public interface Web02FeignTestClient {

    @GetMapping(value = "/req/resp/void")
    void respVoid();


    @GetMapping(value = "/req/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    String reqPath(@PathVariable("id")Long id,
                          @RequestParam(value = "name", required = false) String name);

    @GetMapping(value = "/req/resp/param", produces = MediaType.APPLICATION_JSON_VALUE)
    String reqParam(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "pageNum", required = false) Integer pageNum,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @PostMapping(value = "/req/resp/list")
    List<String> nameList(@RequestBody List<String> nameList);

    @PostMapping(value = "/req/resp/map")
    Map<String, Object> reqRespMap(@RequestBody Map<String, Object> map);

    @PostMapping(value = "/req/resp/demo-vo")
    DemoVo reqRespDemoVo(@RequestBody DemoVo demoVo);

    @GetMapping(value = "/resp/result", produces = MediaType.APPLICATION_JSON_VALUE)
    Result<DemoVo> respResult(@RequestParam(value = "name", required = false) String name);

}
