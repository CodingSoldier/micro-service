package com.github.codingsoldier.starters.wagger;

import com.github.codingsoldier.starters.wagger.annotation.ConditionalOnStarterSwaggerEnabled;
import org.springframework.context.annotation.Import;

@Import({SwaggerConfig.class})
@ConditionalOnStarterSwaggerEnabled
public class StarterSwaggerAutoConfiguration {
}
