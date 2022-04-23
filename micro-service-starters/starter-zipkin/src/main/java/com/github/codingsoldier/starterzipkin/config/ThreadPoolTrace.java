package com.github.codingsoldier.starterzipkin.config;

import com.github.codingsoldier.common.util.ThreadUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * 配置线程池，使线程池中执行的任务也有TraceId
 */
@Configuration(proxyBeanMethods = false)
public class ThreadPoolTrace {

    private static Executor traceExecutor;

    private BeanFactory beanFactory;

    public ThreadPoolTrace(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取 Executor
     * @return Executor
     */
    public static Executor getExecutor() {
        return traceExecutor;
    }

    @PostConstruct
    private void init(){
        traceExecutor = new LazyTraceExecutor(beanFactory, ThreadUtil.getExecutor());
    }

}