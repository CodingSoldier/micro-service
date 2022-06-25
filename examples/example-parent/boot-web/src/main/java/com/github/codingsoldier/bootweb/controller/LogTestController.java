package com.github.codingsoldier.bootweb.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.bootweb.dto.PageDto;
import com.github.codingsoldier.bootweb.dto.UserAddDto;
import com.github.codingsoldier.bootweb.dto.UserUpdateDto;
import com.github.codingsoldier.bootweb.entity.UserEntity;
import com.github.codingsoldier.bootweb.vo.UserDetailVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import com.github.codingsoldier.starter.web.util.CopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api(tags = "日志测试API")
@Slf4j
@RestController
@RequestMapping("/log")
public class LogTestController {

    @GetMapping("/test/msg")
    public String getTestMsg(String str) {

        log.debug("***** debug ***** {}", str);
        log.info("***** info ***** {}", str);
        log.warn("***** warn ***** {}", str);
        log.error("***** error ***** {}", str);


        log.error("异常", new RuntimeException(str));

        log.info("########## end ##########");
        return str;
    }

    @GetMapping("/test/while")
    public String testWhile(String str) throws Exception {
        while (true) {
            log.debug("***** debug ***** {}", str);
            log.info("***** info ***** {}", str);
            log.warn("***** warn ***** {}", str);
            log.error("***** error ***** {}", str);


            log.error("异常", new RuntimeException(str));

            log.info("########## end ##########");

            TimeUnit.NANOSECONDS.sleep(10L);
            if (StringUtils.equals("break", str)) {
                break;
            }
        }
        return str;
    }


    @PostMapping("/testRequestBody")
    public UserUpdateDto updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setName("返回值返回值返回值返回值返回值");
        return userUpdateDto;
    }

    @GetMapping("/testParam/id")
    public String pageMapper(@PathVariable("id")Long id, @Param("msg") String msg, String name) {
        return msg;
    }

    @GetMapping("/testModelAttribute")
    public PageDto pageMapper(@ModelAttribute PageDto pageDto) {
        pageDto.setName("返回返回返回返回返回返回");
        return pageDto;
    }

}