package com.github.codingsoldier.starter.sleuthzipkin;

import com.github.codingsoldier.starter.sleuthzipkin.annotation.ConditionalOnStarterSleuthZipkinEnabled;
import com.github.codingsoldier.starter.sleuthzipkin.config.ThreadPoolTraceUtil;
import org.springframework.context.annotation.Import;

@Import({ThreadPoolTraceUtil.class})
@ConditionalOnStarterSleuthZipkinEnabled
public class StarterSleuthZipkinAutoConfiguration {
}
