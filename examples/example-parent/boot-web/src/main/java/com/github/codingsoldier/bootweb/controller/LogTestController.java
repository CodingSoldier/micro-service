package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.dto.PageDto;
import com.github.codingsoldier.bootweb.dto.UserUpdateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Tag(name = "日志测试API")
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