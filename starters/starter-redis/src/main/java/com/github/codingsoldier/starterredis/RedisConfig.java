package com.github.codingsoldier.starterredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 */
@Configuration
@EnableCaching
public class RedisConfig {

    private static final Log logger = LogFactory.getLog(RedisConfig.class);

    // /**
    //  * 自定义key规则
    //  * @return
    //  */
    // @Bean
    // public KeyGenerator keyGenerator() {
    //     return new KeyGenerator() {
    //         @Override
    //         public Object generate(Object target, Method method, Object... params) {
    //             StringBuilder sb = new StringBuilder();
    //             sb.append(target.getClass().getName());
    //             sb.append(method.getName());
    //             for (Object obj : params) {
    //                 sb.append(obj.toString());
    //             }
    //             return sb.toString();
    //         }
    //     };
    // }

    /**
     * 设置RedisTemplate规则
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        if (logger.isDebugEnabled()) {
            logger.debug("配置 RedisTemplate");
        }

        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = ObjectMapperUtil.newObjectMapper();
        // 避免opsForValue()设置bean报错，Redis使用
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 序列化 key value
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    // /**
    //  * 设置CacheManager缓存规则
    //  * @param factory
    //  * @return
    //  */
    // @Bean
    // public CacheManager cacheManager(RedisConnectionFactory factory) {
    //     RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    //     Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
    //
    //     jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
    //
    //     // 配置序列化（解决乱码的问题）
    //     RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
    //             .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
    //             .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
    //             .disableCachingNullValues();
    //
    //     RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
    //             .cacheDefaults(config)
    //             .build();
    //     return cacheManager;
    // }

}
