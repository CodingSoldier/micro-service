package com.github.codingsoldier.example.cloudweb02.controller;

import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.common.exception.MicroServiceException;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
@RequestMapping("/feign02/exception")
public class FeignExceptionController {

    @GetMapping(value = "/name")
    public Map<String, Object> name(@RequestParam(value = "name") String name) throws Exception{
        if (StringUtils.equals("IOException", name)) {
            throw new IOException("测试IO异常");
        } else if (StringUtils.equals("NullPointerException", name)) {
            Integer a = null;
            int i = 1 / a;
            log.info("{}", i);
        } else if (StringUtils.equals("RuntimeException", name)) {
            throw new RuntimeException("resultTypeNotChange-RuntimeException");
        } else if (StringUtils.equals("HttpStatus4xxException", name)) {
            throw new HttpStatus4xxException("resultTypeNotChange-测试HttpStatus4xxException异常");
        } else if (StringUtils.equals("HttpStatus5xxException", name)) {
            throw new HttpStatus5xxException("resultTypeNotChange-测试HttpStatus5xxException异常");
        } else if (StringUtils.equals("MicroServiceException", name)) {
            throw new MicroServiceException(40020, "resultTypeNotChange-测试MicroServiceException异常");
        } else if (StringUtils.equals("Exception", name)) {
            throw new Exception("resultTypeNotChange-测试Exception");
        }
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("key", "执行完了");
        return resp;
    }

    @GetMapping(value = "/result/type/not/change")
    public Result resultTypeNotChange(@RequestParam(value = "name") String name) throws Exception{
        if (StringUtils.equals("IOException", name)) {
            throw new IOException("resultTypeNotChange测试IO异常");
        } else if (StringUtils.equals("NullPointerException", name)) {
            Integer a = null;
            int i = 1 / a;
            log.info("{}", i);
        } else if (StringUtils.equals("RuntimeException", name)) {
            throw new RuntimeException("resultTypeNotChange-RuntimeException");
        } else if (StringUtils.equals("HttpStatus4xxException", name)) {
            throw new HttpStatus4xxException("resultTypeNotChange-测试HttpStatus4xxException异常");
        } else if (StringUtils.equals("HttpStatus5xxException", name)) {
            throw new HttpStatus5xxException("resultTypeNotChange-测试HttpStatus5xxException异常");
        } else if (StringUtils.equals("MicroServiceException", name)) {
            throw new MicroServiceException(40020, "resultTypeNotChange-测试MicroServiceException异常");
        } else if (StringUtils.equals("Exception", name)) {
            throw new Exception("resultTypeNotChange-测试Exception");
        }

        return Result.success("resultTypeNotChange，返回值也是Result");
    }

}