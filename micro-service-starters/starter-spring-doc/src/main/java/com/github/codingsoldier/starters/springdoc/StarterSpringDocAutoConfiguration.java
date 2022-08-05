package com.github.codingsoldier.starters.springdoc;

import com.github.codingsoldier.starters.springdoc.annotation.ConditionalOnStarterSpringDocEnabled;
import org.springframework.context.annotation.Import;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({SpringDocConfig.class})
@ConditionalOnStarterSpringDocEnabled
public class StarterSpringDocAutoConfiguration {
}
