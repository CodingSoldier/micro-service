package com.github.codingsoldier.example.cloudweb02.service;

import com.github.codingsoldier.common.util.ThreadUtil;
import com.github.codingsoldier.starterzipkin.config.ThreadPoolTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Web02ServiceImpl implements Web02Service{

    @Override
    public String getTestVal(String name){
        ThreadUtil.execute(() -> {
            log.info("线程池中打印日志");
        });
        log.info("不不不在在线程池中打印日志");
        return "service";
    }

    @Override
    @Async
    public String testThreadPoolTraceId(String name) {
        ThreadPoolTrace.getExecutor().execute(() -> {
            log.info("线程池中打印日志");
        });
        log.info("不不不在在线程池中打印日志");
        return "service";
    }
}