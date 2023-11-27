package com.github.codingsoldier.example.cloudweb01.knife4j;


import com.github.codingsoldier.common.resp.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@ApiSupport(order = 1)
@Tag(name = "测试01-API")
@Slf4j
@RestController
@RequestMapping("/s01")
public class APi01Controller {

    @ApiOperationSupport(order = 10)
    @Operation(summary = "测试01-新增")
    @PostMapping("/add")
    public Result<Detail01VO> addUser(@RequestBody Add01DTO add01Dto) {
        log.info("请求参数：{}", add01Dto);
        return Result.success(new Detail01VO());
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "用户-分页")
    @GetMapping("/page")
    public Result<Detail01VO> page(@ModelAttribute @ParameterObject Page01DTO pageDto) {
        log.info("请求参数：{}", pageDto);
        return Result.success(new Detail01VO());
    }

}
