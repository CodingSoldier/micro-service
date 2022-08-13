package com.github.codingsoldier.example.cloudweb02.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/sentinel")
public class SentinelWeb02Controller {

    /**
     * 服务1调用此方法，测试下游服务使用sentinel
     */
    @GetMapping(value = "/from-nacos")
    @SentinelResource(
            value = "web02Rule",
            blockHandlerClass = SimpleBlockWeb02Handler.class,
            blockHandler = "blockExceptionSimpleHandler"
    )
    public Result<String> fromNacos() {
        return Result.success("web02-fromNacos");
    }

}
