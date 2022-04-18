package com.github.codingsoldier.starterweb;

import com.github.codingsoldier.common.util.OkHttpUtil;
import com.github.codingsoldier.starterweb.resp.Result;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class OkHttpUtilTest {


    public static void main(String[] args) {
        Result result = OkHttpUtil.get("http://localhost:8080/example/validations/account?userId=1&account", Result.class);
        System.out.println(result);

        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", 1);
        params.put("account", "账号");
        result = OkHttpUtil.get("http://localhost:8080/example/validations/account", params, Result.class);
        System.out.println(result);

        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> vo = new HashMap<>();
        body.put("userId", 1);
        body.put("userName", "sdsdff");
        body.put("strList", Lists.newArrayList("a"));
        body.put("validation2Vo", vo);
        vo.put("jobId", 111);
        vo.put("jobName", "234324");
        result = OkHttpUtil.post("http://localhost:8080/example/validations/add", body, Result.class);
        System.out.println(result);

    }


}
