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
import com.github.codingsoldier.common.util.objectmapper.serializer.DateTimeSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "framework.starter.web.enableWebMvcConfig", matchIfMissing = true)
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Log logger = LogFactory.getLog(WebMvcConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (logger.isDebugEnabled()) {
            logger.debug("设置允许跨域");
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
                // 跨域允许时间
                .maxAge(3600);
    }

    /**
     * 时间转换
     *
     * @param converters
     */
    @SuppressWarnings("squid:S125")
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (logger.isDebugEnabled()) {
            logger.debug("配置 configureMessageConverters");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //序列化的时候序列对象的所有属性
        // objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候,不抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 时间转换为时间戳。
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeSerializer dateTimeSerializer = new DateTimeSerializer();
        javaTimeModule.addSerializer(Date.class, dateTimeSerializer);
        javaTimeModule.addSerializer(LocalDate.class, dateTimeSerializer);
        javaTimeModule.addSerializer(LocalDateTime.class, dateTimeSerializer);
        javaTimeModule.addSerializer(OffsetDateTime.class, dateTimeSerializer);
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(new Jdk8Module());

        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        converters.add(jackson2HttpMessageConverter);
    }


}
