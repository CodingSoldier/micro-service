package com.github.codingsoldier.starter.nacos;


import com.github.codingsoldier.starter.nacos.graceful.annotation.ConditionalOnStarterNacosGracefulEnabled;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ComponentScan(basePackages = {"com.github.codingsoldier.starter.nacos.graceful"})
@ConditionalOnStarterNacosGracefulEnabled
@PropertySource("classpath:nacos.properties")
public class StarterNacosAutoConfiguration {
}
