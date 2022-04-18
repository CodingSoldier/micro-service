package com.github.codingsoldier.starterredis;

import com.github.codingsoldier.starterredis.annotation.ConditionalOnStarterRedisEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Import({RedisConfig.class})
@ConditionalOnStarterRedisEnabled
public class StarterRedisAutoConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    @ConditionalOnMissingBean(RedisUtil.class)
    public RedisUtil redisUtil(){
        return new RedisUtil(redisTemplate);
    }

}
