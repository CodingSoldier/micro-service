package com.github.codingsoldier.starter.web.config;

import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.core.ContextPropagator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadConfigurationBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4jBulkheadProvider;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Resilience4j线程切换时的MDC上下文传播配置。
 *
 * @author chenpq05
 * @since 2026-06-11
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(Resilience4JCircuitBreakerFactory.class)
public class Resilience4jMdcContextConfiguration {

  /**
   * 为Spring Cloud CircuitBreaker使用的Resilience4j执行器增加MDC传播。
   *
   * @return Resilience4j工厂自定义器
   */
  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> resilience4jMdcContextCustomizer() {
    return factory -> {
      factory.configureExecutorService(newMdcContextExecutor());
      factory.configureGroupExecutorService(group -> newMdcContextExecutor());
    };
  }

  /**
   * 为Resilience4j ThreadPoolBulkhead增加MDC传播。
   *
   * @return Resilience4j Bulkhead提供者自定义器
   */
  @Bean
  public Customizer<Resilience4jBulkheadProvider> resilience4jMdcBulkheadCustomizer() {
    return provider -> provider.configureDefault(id -> new Resilience4jBulkheadConfigurationBuilder()
        .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.custom()
            .contextPropagator(new MdcContextPropagator())
            .build())
        .build());
  }

  private ExecutorService newMdcContextExecutor() {
    return new MdcContextScheduledExecutor(Runtime.getRuntime().availableProcessors());
  }

  private static class MdcContextPropagator implements ContextPropagator<Map<String, String>> {

    @Override
    public Supplier<Optional<Map<String, String>>> retrieve() {
      return () -> Optional.ofNullable(MDC.getCopyOfContextMap());
    }

    @Override
    public Consumer<Optional<Map<String, String>>> copy() {
      return contextMap -> {
        if (contextMap.isPresent()) {
          MDC.setContextMap(contextMap.get());
        } else {
          MDC.clear();
        }
      };
    }

    @Override
    public Consumer<Optional<Map<String, String>>> clear() {
      return contextMap -> MDC.clear();
    }
  }

  private static class MdcContextScheduledExecutor extends ScheduledThreadPoolExecutor {

    MdcContextScheduledExecutor(int corePoolSize) {
      super(corePoolSize);
      setRemoveOnCancelPolicy(true);
    }

    @Override
    public void execute(Runnable command) {
      super.execute(wrap(command));
    }

    @Override
    public Future<?> submit(Runnable task) {
      return super.submit(wrap(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
      return super.submit(wrap(task), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
      return super.submit(wrap(task));
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
      return super.schedule(wrap(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
      return super.schedule(wrap(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
        TimeUnit unit) {
      return super.scheduleAtFixedRate(wrap(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,
        TimeUnit unit) {
      return super.scheduleWithFixedDelay(wrap(command), initialDelay, delay, unit);
    }

    private Runnable wrap(Runnable runnable) {
      Map<String, String> contextMap = MDC.getCopyOfContextMap();
      return () -> {
        Map<String, String> previousContextMap = MDC.getCopyOfContextMap();
        setContextMap(contextMap);
        try {
          runnable.run();
        } finally {
          setContextMap(previousContextMap);
        }
      };
    }

    private <T> Callable<T> wrap(Callable<T> callable) {
      Map<String, String> contextMap = MDC.getCopyOfContextMap();
      return () -> {
        Map<String, String> previousContextMap = MDC.getCopyOfContextMap();
        setContextMap(contextMap);
        try {
          return callable.call();
        } finally {
          setContextMap(previousContextMap);
        }
      };
    }

    private void setContextMap(Map<String, String> contextMap) {
      if (contextMap == null) {
        MDC.clear();
      } else {
        MDC.setContextMap(contextMap);
      }
    }
  }
}
