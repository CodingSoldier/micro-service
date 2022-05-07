package com.github.codingsoldier.example.cloudweb02.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于 Sentinel 控制台配置流控规则
 * Sentinel 是懒加载的, 先去访问一次接口, 才可以在 Sentinel Dashboard 看到接口
 */
@Slf4j
@RestController
@RequestMapping("/sentinel/dashboard")
public class Sentinel01Controller {
    /**
     * 1、在 dashboard 中 "流控规则" 中新增流控规则
     * 2、资源名为“byResource”
     */
    @GetMapping("/by-resource")
    @SentinelResource(
            value = "byResource",
            blockHandlerClass = SimpleBlockHandler.class,
            blockHandler = "blockExceptionSimpleHandler"
    )
    public Result<String> byResource() {
        log.info("coming in rate limit controller by resource");
        return new Result<>(0, "", "byResource");
    }


    /**
     * 1、先发送一次接口请求  http://localhost:8001/cloud-web-01/sentinel/dashboard/by-url
     * 2、刷新 sentinel-dashbord，点击“簇点链路”（簇点链路是所有受sentinel监控的url），即可看到 by-url
     * 3、点击 by-url 的 “流控”，资源名保持为 byUrl
     * 4、设置 单机阀值，也可实现流控
     */
    @GetMapping("/by-url")
    @SentinelResource(
            value = "byUrl",
            blockHandlerClass = SimpleBlockHandler.class,
            blockHandler = "blockExceptionSimpleHandler"
    )
    public Result<String> byUrl() {
        log.info("coming in rate limit controller by url");
        return new Result<>(0, "", "byUrl");
    }

    /**
     * 从 nacos 获取规则
     *
     * @return
     */
    @GetMapping("/from-nacos")
    @SentinelResource(
            value = "fromNacos",
            blockHandlerClass = SimpleBlockHandler.class,
            blockHandler = "blockExceptionSimpleHandler"
    )
    public Result<String> fromNacos() {
        log.info("coming in rate limit controller by fromNacos");
        return new Result<>(0, "", "fromNacos");
    }

    @GetMapping("/gateway01")
    public Result<String> gateway01(String msg) {
        log.info("##########gateway01 msg={}", msg);
        return new Result<>(0, "", "gateway01 " + msg);
    }

    @GetMapping("/gateway02")
    public Result<String> gateway02(String msg) {
        log.info("##########gateway02 msg={}", msg);
        return new Result<>(0, "", "gateway02 " + msg);
    }

}
