package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.starterredis.RedisUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Api(tags = "Redis测试API")
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request){

        RedisUtil.opsForValue().set("for-value:aa", "aaa");
        ValueOperations<String, String> opsForValue = RedisUtil.opsForValue();
        String str = opsForValue.get("for-value:aa");
        log.info("for-value:aa {}", str);

        RedisTestBean redisTestBean = RedisTestBean.builder()
                .id(11L).age(18).name("名字").updateTime(LocalDateTime.now())
                .build();
        RedisUtil.opsForValue().set("for-value:bean", redisTestBean);
        RedisTestBean bean1 = RedisUtil.<String, RedisTestBean>template().opsForValue().get("for-value:bean");
        log.info("获取值 {}", bean1);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("11", 123456);
        map.put("22", 45678);
        RedisUtil.opsForHash().putAll("hashkey", map);
        RedisUtil.opsForHash().put("hashkey", "33", 45678);
        RedisUtil.<String, String, Integer>opsForHash().put("hashkey", "22", 45678);

        HashOperations<String, String, Integer> opsForHash = RedisUtil.opsForHash();
        Integer hashVal = opsForHash.get("hashkey", "22");
        log.info("hashVal {}", hashVal);



        ArrayList<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        RedisUtil.opsForList().leftPushAll("list", list.toArray());
        RedisUtil.opsForList().leftPushAll("list", list.toArray());

        ListOperations<String, String> opsForList = RedisUtil.opsForList();
        String listVal = opsForList.leftPop("list");
        log.info("listVal: {}", listVal);

        return "success";
    }


}