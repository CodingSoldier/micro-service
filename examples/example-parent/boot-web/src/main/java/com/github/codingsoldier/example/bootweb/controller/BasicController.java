package com.github.codingsoldier.example.bootweb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.bootweb.dto.TimeBarDTO;
import com.github.codingsoldier.example.bootweb.dto.TimeSlashDTO;
import com.github.codingsoldier.example.bootweb.dto.UserDTO;
import com.github.codingsoldier.example.bootweb.dto.UserUpdateDTO;
import com.github.codingsoldier.example.bootweb.vo.TimeSlashVO;
import com.github.codingsoldier.example.bootweb.vo.TimeVO;
import com.github.codingsoldier.example.bootweb.vo.UserPageVO;
import com.github.codingsoldier.example.bootweb.vo.UserUpdateVO;
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiSupport(order = 1)
@Tag(name = "基本接口")
@Slf4j
@RestController
@RequestMapping("/basic")
public class BasicController {

    @GetMapping(value = "/timeout", produces = MediaType.APPLICATION_JSON_VALUE)
    public String timeout(@RequestParam("time")Long time) throws Exception {
        TimeUnit.SECONDS.sleep(time);
        return UUID.randomUUID().toString();
    }

    /**
     * knife4j-openapi3“是否必须”是由 @RequestParam(required = )决定，而不是@Parameters()
     */
    @ApiOperationSupport(order = 1)
    @Operation(summary = "GET请求接口")
    @Parameters({
        @Parameter(name = "id",description = "id"),
        @Parameter(name = "msg",description = "信息"),
        @Parameter(name = "name",description = "名称"),
    })
    @GetMapping(value = "/params/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String paramsPath(@PathVariable("id") Long id,
             @RequestParam("msg") String msg,
             @RequestParam(value = "name", required = false) String name) {
        return id + msg + name;
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "GET请求参数-实体")
    @GetMapping(value = "/modelAttribute")
    public Result<PageData<UserPageVO>> modelAttribute(@ModelAttribute @ParameterObject UserDTO dto) {
        dto.setName("junit测试修改名字");
        UserPageVO vo = new UserPageVO();
        BeanUtils.copyProperties(dto, vo);
        PageData<UserPageVO> pageData = PageData.create(Page.of(1L, 10L), List.of(vo));
        return Result.success(pageData);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "POST接口")
    @PostMapping("/body")
    public Result<UserUpdateVO> body(@RequestBody UserUpdateDTO dto) {
        dto.setName("junit测试修改名字");
        UserUpdateVO vo = new UserUpdateVO();
        BeanUtils.copyProperties(dto, vo);
        return Result.success(vo);
    }

    @ApiOperationSupport(order = 11)
    @Operation(summary = "时间-中杠")
    @PostMapping("/time/bar")
    public TimeVO timeBar(@RequestBody TimeBarDTO dto) {
        log.info("请求参数：{}", dto);
        TimeVO resp = new TimeVO();
        BeanUtils.copyProperties(dto, resp);
        return resp;
    }

    @ApiOperationSupport(order = 12)
    @Operation(summary = "时间-斜杠")
    @PostMapping("/time/slash")
    public TimeSlashVO timeSlash(@RequestBody TimeSlashDTO dto) {
        log.info("请求参数：{}", dto);
        TimeSlashVO resp = new TimeSlashVO();
        BeanUtils.copyProperties(dto, resp);
        return resp;
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "不包装")
    @GetMapping(value = "/no-wrapper")
    @NoWrapper
    public TimeVO noWrapper() {
        return TimeVO.builder().id(1L).build();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "读取配置文件")
    @GetMapping(value = "/properties/msg", produces = MediaType.APPLICATION_JSON_VALUE)
    public String propertiesMsg(HttpServletRequest request) {
        String property = ApplicationContextHolder.getApplicationContext().getEnvironment()
                .getProperty("junit-test.msg");
        return property;
    }

}