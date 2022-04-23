package com.github.codingsoldier.starterzipkin;

import com.github.codingsoldier.starterzipkin.annotation.ConditionalOnStarterZipkinEnabled;
import com.github.codingsoldier.starterzipkin.config.ThreadPoolTrace;
import org.springframework.context.annotation.Import;

@Import({ThreadPoolTrace.class})
@ConditionalOnStarterZipkinEnabled
public class StarterZipkinAutoConfiguration {
}
