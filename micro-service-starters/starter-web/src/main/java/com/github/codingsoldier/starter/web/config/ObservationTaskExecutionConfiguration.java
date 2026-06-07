package com.github.codingsoldier.starter.web.config;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.support.ContextPropagatingTaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 基于官方 context propagation 的异步执行器配置。
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
public class ObservationTaskExecutionConfiguration {

  @Bean
  public TaskDecorator taskDecorator() {
    TaskDecorator contextDecorator = new ContextPropagatingTaskDecorator();
    return runnable -> {
      Map<String, String> contextMap = MDC.getCopyOfContextMap();
      Runnable decorated = contextDecorator.decorate(runnable);
      return () -> {
        if (contextMap != null) {
          MDC.setContextMap(contextMap);
        }
        try {
          decorated.run();
        } finally {
          MDC.clear();
        }
      };
    };
  }

  @Primary
  @Bean(name = {"microServiceExecutor"})
  public AsyncTaskExecutor microServiceExecutor(TaskDecorator taskDecorator) {
    SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("micro-service-virtual");
    executor.setVirtualThreads(true);
    executor.setTaskDecorator(taskDecorator);
    return executor;
  }

}
