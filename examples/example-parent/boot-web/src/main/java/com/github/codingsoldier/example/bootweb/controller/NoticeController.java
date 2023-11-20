package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.common.exception.BackendServicesException;
import com.github.codingsoldier.starter.web.notice.workweixin.WorkWxUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "消息测试")
@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping(value = "wx", produces = MediaType.APPLICATION_JSON_VALUE)
    public String wx(HttpServletRequest request) {
        WorkWxUtil.sendAsyn(new BackendServicesException("测试"));
        return "success";
    }

}