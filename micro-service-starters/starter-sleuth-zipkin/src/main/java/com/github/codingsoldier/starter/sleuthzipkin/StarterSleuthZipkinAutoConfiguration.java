package com.github.codingsoldier.starter.sleuthzipkin;

import com.github.codingsoldier.starter.sleuthzipkin.annotation.ConditionalOnStarterSleuthZipkinEnabled;
import com.github.codingsoldier.starter.sleuthzipkin.config.ThreadPoolTraceUtil;
import org.springframework.context.annotation.Import;
/**
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({ThreadPoolTraceUtil.class})
@ConditionalOnStarterSleuthZipkinEnabled
public class StarterSleuthZipkinAutoConfiguration {
}
