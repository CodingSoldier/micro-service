package com.github.codingsoldier.example.cloudweb02;

import com.github.codingsoldier.common.util.ThreadUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * 配置线程池，使线程池中执行的任务也有TraceId
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
public class ThreadTraceConfigurer extends AsyncConfigurerSupport {

    private static Executor traceExecutor;

    @Autowired
    private BeanFactory beanFactory;

    /**
     * 获取 Executor
     * @return Executor
     */
    public static Executor getExecutor() {
        return traceExecutor;
    }

    /**
     * 覆盖 AsyncConfigurerSupport#getAsyncExecutor()
     * 请不要调用此方法，请使用 getExecutor() 方法
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        return new LazyTraceExecutor(beanFactory, ThreadUtil.getExecutor());
    }

    @PostConstruct
    private void init(){
        traceExecutor = getAsyncExecutor();
    }

}