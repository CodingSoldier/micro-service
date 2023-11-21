package com.github.codingsoldier.starter.micrometer.tracing;

import com.github.codingsoldier.starter.micrometer.tracing.annotation.ConditionalOnStarterMicrometerTracingEnabled;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConditionalOnStarterMicrometerTracingEnabled
@PropertySource("classpath:micrometer-tracing.properties")
public class StarterMicrometerTracingAutoConfiguration {
}
