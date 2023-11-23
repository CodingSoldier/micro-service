package com.github.codingsoldier.example.cloudweb01.openapi3;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@Tag(name = "测试01-API")
@Slf4j
@RestController
@RequestMapping("/s01")
public class Swagger01Controller {

    @Operation(summary = "测试01-新增")
    @PostMapping("/add")
    public Detail01Vo addUser(@RequestBody Add01Dto add01Dto) {
        log.info("请求参数：{}", add01Dto);
        return new Detail01Vo();
    }

    @Operation(summary = "用户-分页")
    @GetMapping("/page")
    public Detail01Vo page(@ModelAttribute @ParameterObject Page01Dto pageDto) {
        log.info("请求参数：{}", pageDto);
        return new Detail01Vo();
    }

}
