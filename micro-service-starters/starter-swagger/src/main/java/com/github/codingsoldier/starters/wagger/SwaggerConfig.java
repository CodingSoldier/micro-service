package com.github.codingsoldier.starters.wagger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    private static final Log logger = LogFactory.getLog(SwaggerConfig.class);

    // private final static String groupName = "web";//组群名称

    private final static String headerName = "x-token";//需要swagger每次调接口前携带的头信息的key
    //private final static String headerName2 = "test";//如果要多个请求头信息，自行解放注释

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket createRestApi() {
        if (logger.isDebugEnabled()) {
            logger.debug("创建 IOC Bean Docket");
        }
        return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo())//文档信息
            // .groupName(groupName)//组群名称
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.github.codingsoldier"))//需要扫描的api所在目录
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
