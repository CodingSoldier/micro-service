package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.common.exception.AppException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "全局异常测试-API")
@Slf4j
@RestController
@RequestMapping("/global/exception/handler")
public class GlobalExceptionHandlerController {

    @GetMapping(value = "/test/more/exception", produces = MediaType.APPLICATION_JSON_VALUE)
    public String methodArgument(@RequestParam("id") Long id, @RequestParam("name") String name) throws Exception {
        if (StringUtils.equals("IOException", name)) {
            throw new IOException("测试IO异常");
        } else if (StringUtils.equals("NullPointerException", name)) {
            Integer a = null;
            int i = 1 / a;
            log.info("{}", i);
        } else if (StringUtils.equals("AppException", name)) {
            throw new AppException("测试APP异常");
        } else if (StringUtils.equals("Exception", name)) {
            throw new Exception("测试Exception");
        }
        return "success";
    }

}