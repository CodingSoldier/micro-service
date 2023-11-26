package com.github.codingsoldier.example.bootweb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.bootweb.dto.*;
import com.github.codingsoldier.example.bootweb.vo.HttpTestTimeSlashVO;
import com.github.codingsoldier.example.bootweb.vo.HttpTestVO;
import com.github.codingsoldier.example.bootweb.vo.UserPageVO;
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

    /**
     * knife4j-openapi3“是否必须”是由 @RequestParam(required = )决定，而不是@Parameters()
     */
    @ApiOperationSupport(order = 1)
    @Operation(summary = "GET接口测试")
    @Parameters({
        @Parameter(name = "id",description = "id"),
        @Parameter(name = "msg",description = "信息"),
        @Parameter(name = "name",description = "名称"),
    })
    @GetMapping(value = "/params/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String paramsPath(@PathVariable("id") Long id,
             @RequestParam("msg") String msg,
             @RequestParam(value = "msg", required = false) String name) {
        return id + msg + name;
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "GET接口请求参数实体")
    @GetMapping(value = "/modelAttribute")
    public Result<PageData<UserPageVO>> modelAttribute(@ModelAttribute @ParameterObject UserDTO pageDTO) {
        pageDTO.setName("junit测试修改名字");
        UserPageVO userPageVO = new UserPageVO();
        BeanUtils.copyProperties(pageDTO, userPageVO);
        PageData<UserPageVO> pageData = PageData.create(Page.of(1L, 10L), List.of(userPageVO));
        return Result.success(pageData);
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "POST接口测试")
    @PostMapping("/body")
    public Result<UserUpdateDTO> body(@RequestBody UserUpdateDTO userUpdateDto) {
        userUpdateDto.setName("junit测试修改名字");
        return Result.success(userUpdateDto);
    }


    @ApiOperationSupport(order = 1)
    @PostMapping("/time")
    public HttpTestVO timeSlash(@RequestBody HttpTimeDTO httpTimeDto) {
        log.info("请求参数：{}", httpTimeDto);
        HttpTestVO httpTestVo = new HttpTestVO();
        BeanUtils.copyProperties(httpTimeDto, httpTestVo);
        return httpTestVo;
    }


    @ApiOperationSupport(order = 1)
    @PostMapping("/time/bar")
    public HttpTestVO timeBar(@RequestBody HttpTestDTO httpTestDto) {
        log.info("请求参数：{}", httpTestDto);
        HttpTestVO resp = new HttpTestVO();
        BeanUtils.copyProperties(httpTestDto, resp);
        return resp;
    }

    @ApiOperationSupport(order = 1)
    @PostMapping("/time/slash")
    public HttpTestTimeSlashVO timeSlash(@RequestBody HttpTestTimeSlashDTO httpTestTimeSlashDto) {
        log.info("请求参数：{}", httpTestTimeSlashDto);
        HttpTestTimeSlashVO resp = new HttpTestTimeSlashVO();
        BeanUtils.copyProperties(httpTestTimeSlashDto, resp);
        return resp;
    }

    @ApiOperationSupport(order = 1)
    @GetMapping(value = "/no-wrapper")
    @NoWrapper
    public HttpTestVO noWrapper() {
        return HttpTestVO.builder().id(1L).build();
    }

    @ApiOperationSupport(order = 1)
    @GetMapping(value = "/properties/msg", produces = MediaType.APPLICATION_JSON_VALUE)
    public String propertiesMsg(HttpServletRequest request) {
        String property = ApplicationContextHolder.getApplicationContext().getEnvironment()
                .getProperty("junit-test.msg");
        return property;
    }

}