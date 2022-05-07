package com.github.codingsoldier.starter.sleuthzipkin.config;

import com.github.codingsoldier.common.util.ThreadUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 配置线程池，使线程池中执行的任务也有TraceId
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
public class ThreadPoolTraceUtil {

    private static LazyTraceExecutor traceExecutor;

    private BeanFactory beanFactory;

    public ThreadPoolTraceUtil(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 在公共线程池中执行任务
     *
     * @param runnable 可运行对象
     */
    public static void execute(Runnable runnable) {
        traceExecutor.execute(runnable);
    }

    @PostConstruct
    private void init() {
        traceExecutor = new LazyTraceExecutor(beanFactory, ThreadUtil.getExecutor());
    }

}