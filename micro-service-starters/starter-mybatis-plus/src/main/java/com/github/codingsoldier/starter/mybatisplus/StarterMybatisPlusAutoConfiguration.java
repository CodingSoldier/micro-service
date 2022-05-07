package com.github.codingsoldier.starter.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.github.codingsoldier.starter.mybatisplus.annotation.ConditionalOnStarterMybatisPlusEnabled;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Import 的作用：当CpqRedisAutoConfiguration注入IOC容器时，将CpqRedisConfig也注入IOC容器
 * proxyBeanMethods = false 的作用：每次从 IOC 容器获取 MybatisAutoConfiguration 实例，创建一个新的bean
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({CustomMetaObjectHandler.class})
@Configuration(proxyBeanMethods = false)
@ConditionalOnStarterMybatisPlusEnabled
public class StarterMybatisPlusAutoConfiguration {

    private static final Log logger = LogFactory.getLog(StarterMybatisPlusAutoConfiguration.class);

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        if (logger.isDebugEnabled()) {
            logger.debug("创建 IOC Bean mybatisPlusInterceptor");
        }
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 新的分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


}
