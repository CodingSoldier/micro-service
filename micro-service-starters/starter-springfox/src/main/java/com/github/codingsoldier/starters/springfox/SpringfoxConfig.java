package com.github.codingsoldier.starters.springfox;

import com.github.codingsoldier.starters.springfox.properties.StarterSpringfoxProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@SuppressWarnings("squid:S125")
@EnableConfigurationProperties(StarterSpringfoxProperties.class)
@Configuration(proxyBeanMethods = false)
@EnableOpenApi
public class SpringfoxConfig {

    /**
     * 基础扫描包
     */
    public static final String BASE_PACKAGE = "com.github.codingsoldier";


    private static final Log logger = LogFactory.getLog(SpringfoxConfig.class);

    // private final static String groupName = "web";//组群名称
    //private final static String headerName2 = "test";//如果要多个请求头信息，自行解放注释

    /**
     * 需要swagger每次调接口前携带的头信息的key
     */
    private static final String HEADER_NAME = "x-token";

    public static List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        //配置header头1
        ApiKey tokenAccess = new ApiKey(HEADER_NAME, HEADER_NAME, "header");
        apiKeyList.add(tokenAccess);
        return apiKeyList;
    }

    public static List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        //作用域为全局
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "描述")};
        //为每个api添加请求头
        securityReferenceList.add(new SecurityReference(HEADER_NAME, authorizationScopes));
        //以此类推
        //securityReferenceList.add(new SecurityReference(headerName2, scopes()));
        securityContextList.add(SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .build()
        );
        return securityContextList;
    }

    public static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api文档")
                .description("文档描述")
                // .termsOfServiceUrl("")
                .contact(new Contact("CodingSoldier", "https://github.com/CodingSoldier", ""))
                .version("1.0")
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi(StarterSpringfoxProperties starterSpringFoxProperties) {
        String basePackage = starterSpringFoxProperties.getBasePackage();
        if (BASE_PACKAGE.equals(basePackage)) {
            String msg = String.format("警告：swagger扫描目录为%s，请通过 framework.starter.swagger.base-package 修改扫描目录", basePackage);
            logger.warn(msg);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("starter-swagger 创建 Swagger Docket，详细配置请查看 com.github.codingsoldier.starters.wagger.SwaggerConfig");
        }
        return new Docket(DocumentationType.OAS_30)
                //文档信息
                .apiInfo(apiInfo())
                //组群名称
                // .groupName(groupName)
                .select()
                //需要扫描的api所在目录
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                //匹配全部地址路径
                .paths(PathSelectors.any())
                .build()
                //配置安全方案
                .securitySchemes(securitySchemes())
                //配置安全方案所实现的上下文
                .securityContexts(securityContexts())
                ;
    }

}
