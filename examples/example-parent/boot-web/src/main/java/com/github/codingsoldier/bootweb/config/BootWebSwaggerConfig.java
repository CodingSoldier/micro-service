// package com.github.codingsoldier.bootweb.config;
//
// import com.github.codingsoldier.starterswagger.SwaggerConfig;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.oas.annotations.EnableOpenApi;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.service.Contact;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
//
// /**
//  * 覆盖 starter-swagger 的默认配置类
//  * 步骤：
//  *      1、配置 framework.starter.swagger.enabled = false
//  *      2、新建Swagger配置类
//  *
//  * swagger地址
//  *   http://localhost:8080/example/swagger-ui/index.html
//  */
// @Configuration
// @EnableOpenApi
// @Slf4j
// public class BootWebSwaggerConfig {
//
//     @Bean
//     public Docket createRestApi() {
//         log.info("##########初始化WebTestSwaggerConfig");
//         return new Docket(DocumentationType.OAS_30)
//             .apiInfo(apiInfo())//文档信息
//             // .groupName(groupName)//组群名称
//             .select()
//             .apis(RequestHandlerSelectors.basePackage("com.github.codingsoldier.bootweb"))//需要扫描的api所在目录
//             .paths(PathSelectors.any())//匹配全部地址路径
//             .build()
//             .securitySchemes(SwaggerConfig.securitySchemes())//配置安全方案
//             .securityContexts(SwaggerConfig.securityContexts())//配置安全方案所实现的上下文
//             ;
//     }
//
//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//             .title("example-web Api文档")
//             .description("自定义文档描述信息")
//             // .termsOfServiceUrl("")
//             .contact(new Contact("联系人", "联系地址", "联系邮箱"))
//             .version("1.0")
//             .build();
//     }
//
// }
