package com.github.codingsoldier.starter.redis;

import com.github.codingsoldier.starter.redis.annotation.ConditionalOnStarterRedisEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Import({RedisConfig.class})
@ConditionalOnStarterRedisEnabled
public class StarterRedisAutoConfiguration {

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @PostConstruct
    public void init() {
        RedisUtil.setOnceRedisTemplate(redisTemplate);
    }

}
