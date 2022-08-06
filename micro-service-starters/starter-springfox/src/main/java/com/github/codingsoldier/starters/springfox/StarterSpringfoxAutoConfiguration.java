package com.github.codingsoldier.starters.springfox;

import com.github.codingsoldier.starters.springfox.annotation.ConditionalOnStarterSpringfoxEnabled;
import org.springframework.context.annotation.Import;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({SpringfoxConfig.class})
@ConditionalOnStarterSpringfoxEnabled
public class StarterSpringfoxAutoConfiguration {
}
