package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.StringUtils;
import com.github.codingsoldier.example.cloudweb02.vo.DemoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/feign02/resp/type")
public class FeignRespTypeController {

    @GetMapping("/map")
    public Map map(@RequestParam(value = "name", required = false) String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    @GetMapping("/map/error")
    public Map mapError(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.equals("AppException", name)) {
            throw new AppException(ResponseCodeEnum.BAD_REQUEST, "/map/error抛出异常了");
        }
        if (StringUtils.equals("NullPointerException", name)) {
            int a = 0;
            int i = 1 / a;
        }
        return new HashMap();
    }

    @GetMapping("/void")
    public void testVoid(@RequestParam(value = "name", required = false) String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
    }

    @GetMapping("/string")
    public String string(@RequestParam(value = "name", required = false) String name) {
        return "string" + name;
    }

    @GetMapping("/string/error")
    public String stringError(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.equals("NullPointerException", name)) {
            int a = 0;
            int i = 1 / a;
        }
        return "string" + name;
    }

    @GetMapping("/demo-vo")
    public DemoVo demoVo(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.equals("NullPointerException", name)) {
            int a = 0;
            int i = 1 / a;
        }
        DemoVo demoVo = new DemoVo();
        demoVo.setName(name);
        demoVo.setId(111L);
        demoVo.setStartTime(LocalDateTime.now());
        return demoVo;
    }

    @GetMapping("/result")
    public Result<Map<String, String>> resultMap(@RequestParam(value = "name", required = false) String name) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        return Result.success(map);
    }

}