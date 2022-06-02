package com.github.codingsoldier.starters.wagger;

import com.github.codingsoldier.starters.wagger.annotation.ConditionalOnStarterSwaggerEnabled;
import org.springframework.context.annotation.Import;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({SwaggerConfig.class})
@ConditionalOnStarterSwaggerEnabled
public class StarterSwaggerAutoConfiguration {
}
