package com.github.codingsoldier.starter.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.codingsoldier.common.util.objectmapper.deserializer.DateDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.OffsetDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToTimestampSerializer;
import com.github.codingsoldier.starter.web.interceptor.FeignInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "framework.starter.web.enableWebMvcConfig", matchIfMissing = true)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (logger.isDebugEnabled()) {
            logger.debug("配置为允许跨域");
        }
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                // SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (logger.isDebugEnabled()) {
            logger.debug("添加FeignInterceptor");
        }
        //定义排除访问的路径配置
        String[] excludePaths = new String[]{"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                "/error",
                "/actuator/**"
        };
        registry.addInterceptor(new FeignInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }

    /**
     * 添加自定义 MappingJackson2HttpMessageConverter
     * 注意点：
     *   1、RequestBody有时间字段，建议前端传时间戳。
     *      不支持使用 @JsonFormat、@JsonFormat
     *      支持多种时间格式，详情查看 DatePatternUtil.java
     *   2、ResponseBody有时间字段，后台返回时间戳。
     *      不支持使用 @JsonFormat、@JsonFormat 格式化返回值，
     *      如果需要格式化，请使用 @JsonDeserialize，使用例子 HttpController#timeAnno()
     *
     * @param converters
     */
    @SuppressWarnings("squid:S125")
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (logger.isDebugEnabled()) {
            logger.debug("在 configureMessageConverters 方法中配置 MappingJackson2HttpMessageConverter");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //序列化的时候序列对象的所有属性
        // objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 时间转换。
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        TimeToTimestampSerializer timeToTimestampSerializer = new TimeToTimestampSerializer();
        javaTimeModule.addSerializer(Date.class, timeToTimestampSerializer);
        javaTimeModule.addSerializer(LocalDate.class, timeToTimestampSerializer);
        javaTimeModule.addSerializer(LocalDateTime.class, timeToTimestampSerializer);
        javaTimeModule.addSerializer(OffsetDateTime.class, timeToTimestampSerializer);
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(new Jdk8Module());

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        int i = 0;
        for (; i < converters.size(); i++) {
            if (converters.get(i) instanceof  MappingJackson2HttpMessageConverter) {
                break;
            }
        }
        // 添加自定义的 MappingJackson2HttpMessageConverter
        converters.add(i , jackson2HttpMessageConverter);
    }

}
