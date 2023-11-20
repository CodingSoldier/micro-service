package com.github.codingsoldier.starter.knife4j.config;

import com.github.codingsoldier.starter.knife4j.properties.StarterKnife4jProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author chenpq05
 * @since 2023/11/20 14:37
 */

@Slf4j
@EnableSwagger2WebMvc
@EnableConfigurationProperties(StarterKnife4jProperties.class)
@Configuration(proxyBeanMethods = false)
@PropertySource("classpath:knife4j.properties")
public class Knife4jConfiguration {

  /**
   * 基础扫描包
   */
  public static final String BASE_PACKAGE = "com.github.codingsoldier";

  @Bean
  @ConditionalOnMissingBean(Docket.class)
  public Docket dockerBean(StarterKnife4jProperties starterKnife4jProperties) {
    String basePackage = starterKnife4jProperties.getBasePackage();
    if (BASE_PACKAGE.equals(basePackage)) {
      String msg = String.format("警告：starter-knife4j扫描目录为%s，请通过 micro-service.starter.knife4j.base-package 修改扫描目录", basePackage);
      log.warn(msg);
    }

    if (log.isDebugEnabled()) {
      log.debug("starter-swagger 创建 Swagger Docket，详细配置请查看 com.github.codingsoldier.starter.knife4j.config.Knife4jConfiguration");
    }

    //指定使用Swagger2规范
    Docket docket=new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(new ApiInfoBuilder()
            .title(starterKnife4jProperties.getTitle())
            //描述字段支持Markdown语法
            .description("文档描述")
            .termsOfServiceUrl("https://doc.xiaominfo.com/")
            .contact(new Contact("CodingSoldier", "https://github.com/CodingSoldier", ""))
            .version("1.0")
            .build())
        .select()
        //这里指定Controller扫描包路径
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .paths(PathSelectors.any())
        .build();
    return docket;
  }

}
