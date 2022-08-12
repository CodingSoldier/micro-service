package com.github.codingsoldier.example.cloudweb02.trace;

import com.github.codingsoldier.common.util.ThreadPoolUtil;
import com.github.codingsoldier.starter.sleuth.config.TaskTraceUtil;
import com.github.codingsoldier.starter.sleuth.config.ThreadPoolTraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Slf4j
@Service
public class Web02ServiceImpl implements Web02Service {

    @Override
    public String testTraceId(String name) {

        log.info("###有traceid--不不不在在线程池中打印日志");

        ThreadPoolUtil.execute(() -> {
            log.info("！！！没有traceid--普通线程池中打印日志");
        });

        ThreadPoolTraceUtil.execute(() -> {
            log.info("###有traceid--ThreadPoolTraceUtil线程池中打印日志");
        });

        Future<String> submit = TaskTraceUtil.submit(() -> {
            log.info("###有traceid--TaskTraceUtil提交Callable");
            return "完成";
        });
        return name;
    }

    @Override
    @Async
    public String asyncAnno(String name) {

        log.info("####@Async注解的方法有traceid不不不在在线程池中打印日志");

        ThreadPoolTraceUtil.execute(() -> {
            log.info("####@Async注解使用ThreadPoolTraceUtil，有traceid，线程池中打印日志");
        });

        Future<String> submit = TaskTraceUtil.submit(() -> {
            log.info("###有traceid--TaskTraceUtil提交Callable");
            return "完成";
        });

        return name;
    }
}