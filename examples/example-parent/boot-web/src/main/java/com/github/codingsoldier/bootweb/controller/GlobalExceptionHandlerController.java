package com.github.codingsoldier.bootweb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "全局异常测试-API")
@Slf4j
@RestController
@RequestMapping("/global/exception/handler")
public class GlobalExceptionHandlerController {

    @GetMapping(value = "/params/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String paramsPath(@PathVariable("id")Long id, 
                             @RequestParam("msg") String msg, 
                             String name) {
        return id + msg + name;
    }

}