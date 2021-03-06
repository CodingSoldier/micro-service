package com.github.codingsoldier.starter.sleuth;

import com.github.codingsoldier.starter.sleuth.annotation.ConditionalOnStarterSleuthEnabled;
import com.github.codingsoldier.starter.sleuth.config.ThreadPoolTraceUtil;
import org.springframework.context.annotation.Import;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({ThreadPoolTraceUtil.class})
@ConditionalOnStarterSleuthEnabled
public class StarterSleuthAutoConfiguration {
}
