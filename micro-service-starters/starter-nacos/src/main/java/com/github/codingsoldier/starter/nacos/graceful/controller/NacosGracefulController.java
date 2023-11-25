package com.github.codingsoldier.starter.nacos.graceful.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.starter.nacos.graceful.config.NacosGraceful;
import com.github.codingsoldier.starter.nacos.graceful.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * nacos接口
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RefreshScope
@RestController
public class NacosGracefulController {

    private final NacosGraceful nacosGraceful;

    public NacosGracefulController(NacosGraceful nacosGraceful) {
        this.nacosGraceful = nacosGraceful;
    }

    /**
     * nacos客户端下线
     */
    @GetMapping(value = Constant.URI_DEREGISTER_INSTANCE)
    public Result<String> deregisterInstance() throws NacosException {
        nacosGraceful.deregisterInstance();
        return Result.success("");
    }

}