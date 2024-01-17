package com.github.codingsoldier.example.cloudweb01.micrometer;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenpq05
 * @since 2024/1/17 16:00
 */
@Configuration(proxyBeanMethods = false)
public class MicrometerConfig {

  @Bean
  MeterRegistryCustomizer<MeterRegistry> registryCustom(@Value("${spring.application.name}") String applicationName) {
    return registry -> registry.config().commonTags("application", applicationName);
  }

}
