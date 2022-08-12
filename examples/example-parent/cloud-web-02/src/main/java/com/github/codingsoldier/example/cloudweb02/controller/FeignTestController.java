package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.cloudwebapi.DemoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/feign02/test")
public class FeignTestController {

    @GetMapping(value = "/req/resp/void")
    public void respVoid() {
        log.info("@@@@@@@@@@respVoid");
    }


    @GetMapping(value = "/req/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reqPath(@PathVariable("id")Long id,
                        @RequestParam(value = "name", required = false) String name) {
        log.info("@@@@@@@@@@reqPath");
        return String.format("%s--%s", id, name);
    }

    @GetMapping(value = "/req/resp/param", produces = MediaType.APPLICATION_JSON_VALUE)
    public String reqParam(@RequestParam(value = "name", required = false) String name,
                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                      @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("@@@@@@@@@@reqParam");
        return String.format("%s--%s--%s", name, pageNum, pageSize);
    }

    @PostMapping(value = "/req/resp/list")
    public List<String> nameList(@RequestBody List<String> nameList) {
        log.info("@@@@@@@@@@nameList");
        nameList.add("#########");
        return nameList;
    }

    @PostMapping(value = "/req/resp/map")
    public Map<String, Object> reqRespMap(@RequestBody Map<String, Object> map) {
        log.info("@@@@@@@@@@reqRespMap");
        map.put("newKey", "web02返回");
        return map;
    }

    @PostMapping(value = "/req/resp/demo-vo")
    public DemoVo reqRespDemoVo(@RequestBody DemoVo demoVo) {
        log.info("@@@@@@@@@@reqRespDemoVo");
        demoVo.setDate(demoVo.getDate());
        demoVo.setLocalDate(demoVo.getLocalDate());
        demoVo.setLocalDateTime(demoVo.getLocalDateTime());
        demoVo.setOffsetDateTime(demoVo.getOffsetDateTime());
        return demoVo;
    }

    @GetMapping(value = "/resp/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<DemoVo> respResult(@RequestParam(value = "name", required = false) String name) {
        log.info("@@@@@@@@@@respResult");
        DemoVo demoVo = new DemoVo();
        demoVo.setName("#########"+name);
        demoVo.setDate(new Date());
        demoVo.setLocalDate(LocalDate.now());
        demoVo.setLocalDateTime(LocalDateTime.of(2010, 10, 10, 01, 02, 10));
        demoVo.setOffsetDateTime(OffsetDateTime.now());
        return Result.success(demoVo);
    }

}