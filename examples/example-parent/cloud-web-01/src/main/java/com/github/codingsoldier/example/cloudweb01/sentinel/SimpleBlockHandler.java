package com.github.codingsoldier.example.cloudweb01.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleBlockHandler {

    /**
     * 通用限流处理方法
     * 这个方法必须是 static 的
     */
    public static Result<String> blockExceptionSimpleHandler(BlockException exception) {
        log.error("异常处理，blockExceptionSimpleHandler，rule: {} ruleLimitApp: {} ",
                exception.getRule().toString(), exception.getRuleLimitApp());
        return Result.fail("访问太频繁，请稍后重试。");
    }

}
