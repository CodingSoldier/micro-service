package com.github.codingsoldier.starter.redis;

import com.github.codingsoldier.starter.redis.annotation.ConditionalOnStarterRedisEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({RedisConfig.class})
@ConditionalOnStarterRedisEnabled
public class StarterRedisAutoConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean(RedisUtil.class)
    public RedisUtil redisUtil() {
        return new RedisUtil(redisTemplate);
    }

}
