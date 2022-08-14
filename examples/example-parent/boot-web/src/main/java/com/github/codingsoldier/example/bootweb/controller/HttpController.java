package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.example.bootweb.dto.HttpTestAnnoDto;
import com.github.codingsoldier.example.bootweb.dto.HttpTestDto;
import com.github.codingsoldier.example.bootweb.dto.PageDto;
import com.github.codingsoldier.example.bootweb.dto.UserUpdateDto;
import com.github.codingsoldier.example.bootweb.vo.HttpTestAnnoVo;
import com.github.codingsoldier.example.bootweb.vo.HttpTestVo;
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
    public HttpTestVo time(@RequestBody HttpTestDto httpTestDto) {
        log.info("请求参数：{}", httpTestDto);
        HttpTestVo resp = new HttpTestVo();
        BeanUtils.copyProperties(httpTestDto, resp);
        return resp;
    }

    @PostMapping("/time/anno")
    public HttpTestAnnoVo timeAnno(@RequestBody HttpTestAnnoDto httpTestDto) {
        log.info("请求参数：{}", httpTestDto);
        HttpTestAnnoVo resp = new HttpTestAnnoVo();
        BeanUtils.copyProperties(httpTestDto, resp);
        return resp;
    }

    @GetMapping(value = "/no-wrapper")
    @NoWrapper
    public HttpTestVo noWrapper() {
        return HttpTestVo.builder().id(1L).build();
    }

    @GetMapping(value = "/properties/msg", produces = MediaType.APPLICATION_JSON_VALUE)
    public String propertiesMsg(HttpServletRequest request) {
        String property = ApplicationContextHolder.getApplicationContext().getEnvironment()
                .getProperty("junit-test.msg");
        return property;
    }

}