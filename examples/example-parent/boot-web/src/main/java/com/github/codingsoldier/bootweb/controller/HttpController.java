package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.dto.PageDto;
import com.github.codingsoldier.bootweb.dto.UserUpdateDto;
import com.github.codingsoldier.bootweb.dto.WebTestDto;
import com.github.codingsoldier.bootweb.vo.WebTestVo;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.starter.web.annotation.NoWrapper;
import com.github.codingsoldier.starter.web.context.ApplicationContextHolder;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "http-接口测试-API")
@Slf4j
@RestController
@RequestMapping("/http")
public class HttpController {

    @GetMapping(value = "/params/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String paramsPath(@PathVariable("id")Long id, 
                             @RequestParam("msg") String msg, 
                             String name) {
        return id + msg + name;
    }

    @GetMapping(value = "/modelAttribute")
    public PageDto modelAttribute(@ModelAttribute PageDto pageDto) {
        pageDto.setName("junit测试修改名字");
        return pageDto;
    }

    @PostMapping("/body")
    public UserUpdateDto body(@RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setName("junit测试修改名字");
        return userUpdateDto;
    }

    @PostMapping("/time")
    public WebTestVo time(@RequestBody WebTestDto webTestDto) {
        log.info("请求参数：{}", webTestDto);
        WebTestVo resp = new WebTestVo();
        BeanUtils.copyProperties(webTestDto, resp);
        return resp;
    }

    @GetMapping(value = "/no-wrapper")
    @NoWrapper
    public WebTestVo noWrapper() {
        return WebTestVo.builder().id(1L).build();
    }

    @GetMapping(value = "/properties/msg", produces = MediaType.APPLICATION_JSON_VALUE)
    public String propertiesMsg(HttpServletRequest request) {
        String property = ApplicationContextHolder.getApplicationContext().getEnvironment()
                .getProperty("junit-test.msg");
        return property;
    }

}