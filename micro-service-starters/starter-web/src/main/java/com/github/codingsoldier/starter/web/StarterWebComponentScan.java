package com.github.codingsoldier.starter.web;

import com.github.codingsoldier.starter.web.annotation.ConditionalOnStarterWebEnabled;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ComponentScan("com.github.codingsoldier.starter.web")
@ConditionalOnStarterWebEnabled
@PropertySource("classpath:web.properties")
public class StarterWebComponentScan {
}
