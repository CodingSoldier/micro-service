package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.dto.WebTestDto;
import com.github.codingsoldier.bootweb.vo.WebTestVo;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Web测试API")
@Slf4j
@RestController
@RequestMapping("/web")
public class WebTestController {

    @GetMapping("/test/msg")
    public String getTestMsg(HttpServletRequest request) {
        String property = ApplicationContextHolder.getApplicationContext().getEnvironment()
                .getProperty("test.msg");
        return property;
    }

    @PostMapping("/test/time")
    public WebTestVo testTime(@RequestBody WebTestDto webTestDto) {
        log.info("请求参数：{}", webTestDto);
        WebTestVo resp = new WebTestVo();
        BeanUtils.copyProperties(webTestDto, resp);
        return resp;
    }

    @GetMapping("/test/app/exception")
    public String testAppException() {
        throw new AppException("测试APP异常");
    }

    @GetMapping("/test/exception")
    public String testException() throws Exception {
        throw new Exception("测试Exception");
    }

    @GetMapping("/test/no-wrapper")
    @NoWrapper
    public WebTestVo noWrapper() {
        return WebTestVo.builder().id(1L).build();
    }

}