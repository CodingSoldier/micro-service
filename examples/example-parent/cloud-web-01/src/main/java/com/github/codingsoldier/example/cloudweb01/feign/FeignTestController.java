package com.github.codingsoldier.example.cloudweb01.feign;

import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.cloudwebapi.DemoVo;
import com.github.codingsoldier.example.cloudwebapi.Web02FeignTestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/feign01/test")
public class FeignTestController {
    
    @Autowired
    private Web02FeignTestClient web02FeignTestClient;

    @GetMapping(value = "/req/resp/void", produces = MediaType.APPLICATION_JSON_VALUE)
    public String respVoid() {
        log.info("!!!!!!!!!!!!!!!!!respVoid");
        web02FeignTestClient.respVoid();
        return "success";
    }

    @GetMapping(value = "/req/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reqPath(@PathVariable("id")Long id, 
                        @RequestParam(value = "name", required = false) String name) {
        String resp = web02FeignTestClient.reqPath(id, name);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }
    
    @GetMapping(value = "/req/resp/param", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reqParam(@RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        String resp = web02FeignTestClient.reqParam(name, pageNum, pageSize);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }

    @PostMapping(value = "/req/resp/list")
    public List<String> nameList(@RequestBody List<String> nameList) {
        List<String> resp = web02FeignTestClient.nameList(nameList);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }

    @PostMapping(value = "/req/resp/map")
    public Map<String, Object> reqRespMap(@RequestBody Map<String, Object> map) {
        Map<String, Object> resp = web02FeignTestClient.reqRespMap(map);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }

    @PostMapping(value = "/req/resp/demo-vo")
    public DemoVo reqRespDemoVo(@RequestBody DemoVo demoVo) {
        DemoVo resp = web02FeignTestClient.reqRespDemoVo(demoVo);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }

    @GetMapping(value = "/resp/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<DemoVo> respResult(@RequestParam(value = "name", required = false) String name) {
        Result<DemoVo> resp = web02FeignTestClient.respResult(name);
        log.info("!!!!!!!!!!!!!!!!!resp={}", resp);
        return resp;
    }
    
}