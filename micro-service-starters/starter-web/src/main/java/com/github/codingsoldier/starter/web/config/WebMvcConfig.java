package com.github.codingsoldier.starter.web.config;

import com.github.codingsoldier.starter.web.interceptor.FeignInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer 配置
 * @author chenpq05
 * @since 2022/2/11 12:02
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "micro-service.starter.web.enableWebMvcConfig", matchIfMissing = true)
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
     *
     * 添加自定义 MappingJackson2HttpMessageConverter
     * 前端可以传的时间格式：
     *      时间戳
     *      yyyy-MM-dd、yyyy-MM-dd HH:mm、yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm:ss.SSS
     *      yyyy/MM/dd、yyyy/MM/dd HH:mm:ss
     * 后端返回时间格式：
     *      时间戳
     *      如果需要格式化为其他格式，请使用 @JsonDeserialize，使用例子 HttpController#timeAnno()
     *
     * 注意点：
     *   添加此Converters后不支持使用 @JsonFormat、@DateTimeFormat。
     *   也正是由于这点，注释了代码，不添加此converter
     *
     * @param converters

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
     */

}
