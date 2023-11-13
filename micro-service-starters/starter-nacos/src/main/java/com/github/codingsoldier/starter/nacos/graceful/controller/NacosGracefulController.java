package com.github.codingsoldier.starter.nacos.graceful.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.starter.nacos.graceful.config.NacosGraceful;
import com.github.codingsoldier.starter.nacos.graceful.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RefreshScope
@RestController
public class NacosGracefulController {

    @Autowired
    private NacosGraceful nacosGraceful;

    /**
     * nacos客户端下线
     */
    @GetMapping(value = Constant.URI_DEREGISTER_INSTANCE)
    public Result<String> deregisterInstance() throws NacosException {
        nacosGraceful.deregisterInstance();
        return Result.success("");
    }

}