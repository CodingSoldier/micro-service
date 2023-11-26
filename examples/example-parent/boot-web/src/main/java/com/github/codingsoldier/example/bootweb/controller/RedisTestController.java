package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.example.bootweb.dto.RedisTestBeanDTO;
import com.github.codingsoldier.starter.redis.RedisUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Tag(name = "Redis测试-API")
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test(HttpServletRequest request) {

        RedisUtil.<String, String>opsForValue().set("for-value:aa", "aaa");
        String str = RedisUtil.<String, String>opsForValue().get("for-value:aa");
        log.info("for-value:aa {}", str);

        RedisTestBeanDTO redisTestBean = RedisTestBeanDTO.builder()
                .id(11L).age(18).name("名字").updateTime(LocalDateTime.now())
                .build();
        RedisUtil.opsForValue().set("for-value:bean", redisTestBean);
        RedisTestBeanDTO bean1 = RedisUtil.<String, RedisTestBeanDTO>template().opsForValue().get("for-value:bean");
        log.info("获取值 {}", bean1);

        // HashMap<String, Integer> map = new HashMap<>();
        // map.put("11", 123456);
        // map.put("22", 45678);
        // RedisUtil.opsForHash().putAll("hashkey", map);
        // RedisUtil.opsForHash().put("hashkey", "33", 45678);

        RedisUtil.<String, String, Integer>opsForHash().put("hashkey", "22", 45678);

        Integer hashVal = RedisUtil.<String, String, Integer>opsForHash().get("hashkey", "22");

        log.info("hashVal {}", hashVal);

        ArrayList<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        RedisUtil.opsForList().leftPushAll("list", list.toArray());
        RedisUtil.opsForList().leftPushAll("list", list.toArray());

        ListOperations<String, String> opsForList = RedisUtil.opsForList();
        String listVal = opsForList.leftPop("list");
        log.info("listVal: {}", listVal);

        return bean1.getName();
    }


}