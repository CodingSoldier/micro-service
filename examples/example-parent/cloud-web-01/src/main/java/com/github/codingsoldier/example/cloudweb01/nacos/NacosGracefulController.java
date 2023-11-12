package com.github.codingsoldier.example.cloudweb01.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.github.codingsoldier.common.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.codingsoldier.example.cloudweb01.nacos.constant.Constant.URI_DEREGISTER_INSTANCE;

@Slf4j
@RefreshScope
@RestController
public class NacosGracefulController {

    @Autowired
    private NacosGraceful nacosGraceful;

    @GetMapping(value = "/registerInstance")
    public Result<String> registerInstance() throws NacosException {
        nacosGraceful.registerInstance();
        return Result.success("");
    }

    @GetMapping(value = URI_DEREGISTER_INSTANCE)
    public Result<String> deregisterInstance() throws NacosException {
        nacosGraceful.deregisterInstance();
        return Result.success("");
    }

}