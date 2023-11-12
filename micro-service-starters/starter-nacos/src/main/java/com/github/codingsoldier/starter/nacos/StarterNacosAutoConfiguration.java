package com.github.codingsoldier.starter.nacos;


import com.github.codingsoldier.starter.nacos.annotation.ConditionalOnStarterNacosEnabled;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConditionalOnStarterNacosEnabled
@PropertySource("classpath:nacos.properties")
public class StarterNacosAutoConfiguration {
}
