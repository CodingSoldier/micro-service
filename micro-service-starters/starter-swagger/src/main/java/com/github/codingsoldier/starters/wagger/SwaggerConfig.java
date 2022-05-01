package com.github.codingsoldier.starters.wagger;

import com.github.codingsoldier.starters.wagger.properties.StarterSwaggerProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@EnableConfigurationProperties(StarterSwaggerProperties.class)
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    /**
     * 基础扫描包
     */
    public final static String BASE_PACKAGE = "com.github.codingsoldier";


    private static final Log logger = LogFactory.getLog(SwaggerConfig.class);

    // private final static String groupName = "web";//组群名称
    //private final static String headerName2 = "test";//如果要多个请求头信息，自行解放注释

    /**
     * 需要swagger每次调接口前携带的头信息的key
     */
    private final static String headerName = "x-token";

    @Autowired
    private StarterSwaggerProperties starterSwaggerProperties;

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi() {
        if (logger.isDebugEnabled()) {
            logger.debug("创建 IOC Bean Docket");
        }
        String basePackage = starterSwaggerProperties.getBasePackage();
        if (BASE_PACKAGE.equals(basePackage)){
            String msg = String.format("警告：swagger扫描目录为%s，请通过 framework.starter.swagger.base-package 修改扫描目录", basePackage);
            logger.warn(msg);
        }
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())//文档信息
            // .groupName(groupName)//组群名称
            .select()
            //需要扫描的api所在目录
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())//匹配全部地址路径
            .build()
            .securitySchemes(securitySchemes())//配置安全方案
            .securityContexts(securityContexts())//配置安全方案所实现的上下文
            ;
    }

    public static List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        //配置header头1
        ApiKey token_access = new ApiKey(headerName, headerName, "header");
        apiKeyList.add(token_access);
        return apiKeyList;
    }

    public static List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
        List<SecurityReference> securityReferenceList = new ArrayList<>();
        //作用域为全局
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "描述")};
        //为每个api添加请求头
        securityReferenceList.add(new SecurityReference(headerName, authorizationScopes));
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

}
