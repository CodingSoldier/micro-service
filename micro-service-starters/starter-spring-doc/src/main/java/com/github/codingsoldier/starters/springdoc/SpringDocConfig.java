package com.github.codingsoldier.starters.springdoc;

import com.github.codingsoldier.starters.springdoc.properties.StarterSpringDocProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenpq05
 * @since 2022/8/4 16:44
 */
@EnableConfigurationProperties(StarterSpringDocProperties.class)
@Configuration(proxyBeanMethods = false)
public class SpringDocConfig {

    private static final String X_TOKEN = "x-token";

    private StarterSpringDocProperties properties;

    public SpringDocConfig(StarterSpringDocProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(components())
                .info(apiInfo());
    }

    /**
     * 添加右上角的统一安全认证
     * @return Components
     */
    private Components components() {
        SecurityScheme schemeToken = new SecurityScheme()
                .type(Type.APIKEY)
                .in(In.HEADER)
                .name(X_TOKEN)
                .description("调用登录接口可获得x-token");
        Components components = new Components();
        components.addSecuritySchemes(X_TOKEN, schemeToken);
        return components;
    }

    private Info apiInfo() {
        return new Info().title(properties.getTitle());
    }
}
