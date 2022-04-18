package com.github.codingsoldier.starterswagger;

import com.github.codingsoldier.starterswagger.annotation.ConditionalOnStarterSwaggerEnabled;
import org.springframework.context.annotation.Import;

@Import({SwaggerConfig.class})
@ConditionalOnStarterSwaggerEnabled
public class StarterSwaggerAutoConfiguration {
}
