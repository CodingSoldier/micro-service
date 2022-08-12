package com.github.codingsoldier.starter.sleuth.config;

import com.github.codingsoldier.common.util.ThreadPoolUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 带 traceId 的线程池工具类
 *
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

    @SuppressWarnings("squid:S2696")
    @PostConstruct
    private void init() {
        traceExecutor = new LazyTraceExecutor(beanFactory, ThreadPoolUtil.getExecutor());
    }

    /**
     * 在公共线程池中执行任务
     *
     * @param runnable 可运行对象
     */
    public static void execute(Runnable runnable) {
        traceExecutor.execute(runnable);
    }

}