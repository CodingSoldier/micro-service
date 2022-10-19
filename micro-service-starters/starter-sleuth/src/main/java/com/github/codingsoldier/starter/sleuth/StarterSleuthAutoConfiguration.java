package com.github.codingsoldier.starter.sleuth;

import com.github.codingsoldier.starter.sleuth.annotation.ConditionalOnStarterSleuthEnabled;
import com.github.codingsoldier.starter.sleuth.config.TaskTraceUtil;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({TaskTraceUtil.class})
@ConditionalOnStarterSleuthEnabled
@PropertySource("classpath:sleuth.properties")
public class StarterSleuthAutoConfiguration {
}
