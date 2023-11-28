package com.github.codingsoldier.example.cloudweb02.knife4j;


import com.github.codingsoldier.common.resp.Result;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@ApiSupport(order = 1)
@Tag(name = "测试02-API")
@Slf4j
@RestController
@RequestMapping("/s02")
public class APi02Controller {
    @ApiOperationSupport(order = 10)
    @Operation(summary = "测试02-新增")
    @PostMapping("/add")
    public Result<Detail02VO> addUser(@RequestBody Add02DTO dto) {
        log.info("请求参数：{}", dto);
        return Result.success(new Detail02VO());
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "用户-分页")
    @GetMapping("/page")
    public Result<Detail02VO> page(@ModelAttribute @ParameterObject Page02DTO dto) {
        log.info("请求参数：{}", dto);
        return Result.success(new Detail02VO());
    }

}
