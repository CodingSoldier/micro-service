package com.github.codingsoldier.starterzipkin;

import com.github.codingsoldier.starterzipkin.annotation.ConditionalOnStarterZipkinEnabled;
import com.github.codingsoldier.starterzipkin.config.ThreadPoolTraceUtil;
import org.springframework.context.annotation.Import;

@Import({ThreadPoolTraceUtil.class})
@ConditionalOnStarterZipkinEnabled
public class StarterZipkinAutoConfiguration {
}
