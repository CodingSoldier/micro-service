package com.github.codingsoldier.example.cloudweb02.springdoc;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@Tag(name = "测试swagger02222-API")
@Slf4j
@RestController
@RequestMapping("/s02")
public class Swagger02Controller {

    @Operation(summary = "测试02222-新增")
    @PostMapping("/add")
    public Detail02Vo addUser(@RequestBody Add02Dto add01Dto) {
        log.info("请求参数：{}", add01Dto);
        return new Detail02Vo();
    }

    @Operation(summary = "测试02222-分页")
    @GetMapping("/page")
    public Detail02Vo page(@ModelAttribute @ParameterObject Page02Dto pageDto) {
        log.info("请求参数：{}", pageDto);

        return new Detail02Vo();
    }

}
