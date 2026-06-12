package com.github.codingsoldier.example.cloudweb02.trace;

import static com.github.codingsoldier.common.constant.TraceConstant.X_REQ_TRACE_ID;

import com.github.codingsoldier.common.util.thread.VirtualThreadTraceUtil;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Web02ServiceImpl implements Web02Service {

    @Autowired
    private AsyncTaskExecutor microServiceExecutor;

    @Override
    public String testTraceId(String name) {

        log.info("###有traceid--不不不在在线程池中打印日志");

        VirtualThreadTraceUtil.startVirtualThread(() -> {
            log.info("###有traceid--虚拟线程打印");
        });

        Future<String> virtualThreadSubmit = VirtualThreadTraceUtil.startVirtualThread(() -> {
            log.info("###有traceid--虚拟线程提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });

        microServiceExecutor.execute(() -> {
            log.info("###有traceid--microServiceExecutor线程池中打印日志");
        });

        Future<String> submit = microServiceExecutor.submit(() -> {
            log.info("###有traceid--microServiceExecutor提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });
        try {
            virtualThreadSubmit.get();
            return submit.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
        return "";
    }

    @Async
    @Override
    public void asyncAnno(String name) {

        log.info("####@Async注解的方法有traceid不不不在在线程池中打印日志");

        VirtualThreadTraceUtil.startVirtualThread(() -> {
            log.info("###asyncAnno有traceid--虚拟线程打印");
        });

        Future<String> virtualThreadSubmit = VirtualThreadTraceUtil.startVirtualThread(() -> {
            log.info("###asyncAnno有traceid--虚拟线程提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });

        microServiceExecutor.execute(() -> {
            log.info("####@Async注解使用microServiceExecutor，有traceid，线程池中打印日志");
        });

        Future<String> submit = microServiceExecutor.submit(() -> {
            log.info("###有traceid--microServiceExecutor提交Callable");
            return MDC.get(X_REQ_TRACE_ID);
        });
        try {
            virtualThreadSubmit.get();
            submit.get();
        } catch (Exception e) {
            log.error("异常", e);
        }
    }
}
