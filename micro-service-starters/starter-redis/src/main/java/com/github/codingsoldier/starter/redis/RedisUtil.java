package com.github.codingsoldier.starter.redis;

import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@SuppressWarnings("squid:S3740")
public class RedisUtil{

    private RedisUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    private static RedisTemplate redisTemplate;

    /**
     * 初始化RedisUtil，已在 StarterRedisAutoConfiguration#init() 中初始化
     * @see com.github.codingsoldier.starter.redis.StarterRedisAutoConfiguration#StarterRedisAutoConfiguration(RedisTemplate)
     * @param redisTemplate redisTemplate
     */
    public static synchronized void setOnceRedisTemplate(RedisTemplate redisTemplate) {
        if (RedisUtil.redisTemplate == null) {
            RedisUtil.redisTemplate = redisTemplate;
        }
    }

    /**
     * 获取RedisTemplate
     *
     * @return RedisTemplate<K, V>
     */
    public static <K, V> RedisTemplate<K, V> template() {
        return redisTemplate;
    }

    /**
     * 获取ValueOperations
     *
     * @return ValueOperations<K, V>
     */
    public static <K, V> ValueOperations<K, V> opsForValue() {
        return redisTemplate.opsForValue();
    }

    /**
     * 获取ListOperations
     *
     * @return ListOperations
     */
    public static <K, V> ListOperations<K, V> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * 获取SetOperations
     *
     * @return SetOperations
     */
    public static <K, V> SetOperations<K, V> opsForSet() {
        return redisTemplate.opsForSet();
    }

    /**
     * 获取SetOperations
     *
     * @return ZSetOperations
     */
    public static <K, V> ZSetOperations<K, V> opsForZSet() {
        return redisTemplate.opsForZSet();
    }

    /**
     * 获取HashOperations
     *
     * @return HashOperations
     */
    @SuppressWarnings("squid:S119")
    public static <K, HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * key是否存在
     *
     * @param key key
     * @return Boolean
     */
    public static Boolean hasKey(Object key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置值
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public static void set(Object key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置值
     *
     * @param key   键
     * @param value 值
     */
    public static void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取值
     *
     * @param key       键
     * @return V
     */
    public static <V> V get(Object key) {
        Object value = redisTemplate.opsForValue().get(key);
        return (V) value;
    }

}
