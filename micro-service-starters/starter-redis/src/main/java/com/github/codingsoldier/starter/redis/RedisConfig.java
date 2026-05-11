package com.github.codingsoldier.starter.redis;

import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

/**
 * redis配置类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@SuppressWarnings("squid:S125")
@Configuration(proxyBeanMethods = false)
@EnableCaching
public class RedisConfig {

  private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

  /**
   * 配置序列化规则
   *
   * @param redisConnectionFactory redisConnectionFactory
   * @return RedisTemplate
   */
  @Primary
  @Bean(name = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    if (logger.isDebugEnabled()) {
      logger.debug("配置 RedisTemplate");
    }

    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);

    ObjectMapper objectMapper = redisObjectMapper();
    GenericJacksonJsonRedisSerializer redisSerializer =
        new GenericJacksonJsonRedisSerializer(objectMapper);

    // 序列化 key value
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(redisSerializer);

    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(redisSerializer);

    redisTemplate.afterPropertiesSet();

    return redisTemplate;
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    GenericJacksonJsonRedisSerializer redisSerializer =
        new GenericJacksonJsonRedisSerializer(redisObjectMapper());
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(SerializationPair.fromSerializer(redisSerializer)))
        .build();
  }

  private ObjectMapper redisObjectMapper() {
    BasicPolymorphicTypeValidator polymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
        .allowIfSubType("java.")
        .allowIfSubType("javax.")
        .allowIfSubType("com.github.codingsoldier.")
        .allowIfSubType("org.springframework.")
        .build();
    return ObjectMapperUtil.newObjectMapper().rebuild()
        .activateDefaultTyping(polymorphicTypeValidator, DefaultTyping.NON_FINAL)
        .build();
  }

}
