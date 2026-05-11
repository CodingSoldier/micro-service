package com.github.codingsoldier.starter.web.config;

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
    return new ContextPropagatingTaskDecorator();
  }

  @Primary
  @Bean(name = {"taskExecutor", "applicationTaskExecutor"})
  public AsyncTaskExecutor taskExecutor(TaskDecorator taskDecorator) {
    SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("micro-service-");
    executor.setVirtualThreads(true);
    executor.setTaskDecorator(taskDecorator);
    return executor;
  }

}
