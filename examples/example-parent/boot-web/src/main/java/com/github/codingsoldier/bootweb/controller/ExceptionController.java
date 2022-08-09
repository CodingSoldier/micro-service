package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.common.exception.AppException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "异常测试-API")
@Slf4j
@RestController
@RequestMapping("/ex")
public class ExceptionController {

    @GetMapping(value = "/app/exception", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testAppException() {
        throw new AppException("测试APP异常");
    }

    @GetMapping(value = "/exception", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testException() throws Exception {
        throw new Exception("测试Exception");
    }

}