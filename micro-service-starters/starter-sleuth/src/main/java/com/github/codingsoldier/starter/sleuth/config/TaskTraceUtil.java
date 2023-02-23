package com.github.codingsoldier.starter.sleuth.config;

import com.github.codingsoldier.common.util.thread.CustomAbortPolicy;
import com.github.codingsoldier.common.util.thread.CustomThreadFactory;
import com.github.codingsoldier.common.util.thread.ThreadPoolUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceAsyncTaskExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 带 traceId 的线程池 Callable 工具类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Configuration(proxyBeanMethods = false)
public class TaskTraceUtil {

    private static LazyTraceAsyncTaskExecutor taskExecutor;

    private BeanFactory beanFactory;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    public TaskTraceUtil(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @SuppressWarnings("squid:S2696")
    @PostConstruct
    private void init() {
        int cpuCores = Runtime.getRuntime().availableProcessors();
        cpuCores = Math.min(cpuCores, 5);
        threadPoolTaskExecutor.setCorePoolSize(cpuCores);
        threadPoolTaskExecutor.setMaxPoolSize(cpuCores * 10);
        threadPoolTaskExecutor.setQueueCapacity(500000);
        threadPoolTaskExecutor.setThreadFactory(new CustomThreadFactory("TaskTraceUtil-"));
        threadPoolTaskExecutor.setRejectedExecutionHandler(new CustomAbortPolicy());
        threadPoolTaskExecutor.initialize();
        TaskTraceUtil.taskExecutor = new LazyTraceAsyncTaskExecutor(beanFactory, threadPoolTaskExecutor);
    }

    /**
     * 在公共线程池中执行任务
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return taskExecutor.submit(task);
    }

    /**
     * 运行任务
     * @param task
     */
    public static void execute(Runnable task) {
        taskExecutor.execute(task);
    }

    /**
     * 运行任务
     * @param task
     */
    public void execute(Runnable task, long startTimeout) {
        taskExecutor.execute(task, startTimeout);
    }

}