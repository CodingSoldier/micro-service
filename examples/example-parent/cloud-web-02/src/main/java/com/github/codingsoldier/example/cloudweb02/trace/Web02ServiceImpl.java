package com.github.codingsoldier.example.cloudweb02.trace;

import com.github.codingsoldier.common.util.thread.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

import static com.github.codingsoldier.common.constant.TraceConstant.X_REQ_TRACE_ID;

@Slf4j
@Service
public class Web02ServiceImpl implements Web02Service {

    @Autowired
    private AsyncTaskExecutor microServiceExecutor;

    @Override
    public String testTraceId(String name) {

        log.info("###有traceid--不不不在在线程池中打印日志");

        ThreadPoolUtil.execute(() -> {
            log.info("！！！没有traceid--普通线程池中打印日志");
        });

        microServiceExecutor.execute(() -> {
            log.info("###有traceid--ThreadPoolTraceUtil线程池中打印日志");
        });

        Future<String> submit = microServiceExecutor.submit(() -> {
            log.info("###有traceid--TaskTraceUtil提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });
        try {
            return submit.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
        return null;
    }

    @Override
    public String asyncAnno(String name) {

        log.info("####@Async注解的方法有traceid不不不在在线程池中打印日志");

        microServiceExecutor.execute(() -> {
            log.info("####@Async注解使用ThreadPoolTraceUtil，有traceid，线程池中打印日志");
        });

        Future<String> submit = microServiceExecutor.submit(() -> {
            log.info("###有traceid--TaskTraceUtil提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });
        try {
            return submit.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
        return null;
    }
}
