package com.github.codingsoldier.starter.redis;

import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class RedisUtil<K, V> {

    private static RedisTemplate redisTemplate;

    public RedisUtil(RedisTemplate<K, V> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 获取RedisTemplate
     *
     * @return
     */
    public static <K, V> RedisTemplate<K, V> template() {
        return redisTemplate;
    }

    /**
     * 获取ValueOperations
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> ValueOperations<K, V> opsForValue() {
        return redisTemplate.opsForValue();
    }

    /**
     * 获取ListOperations
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> ListOperations<K, V> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * 获取SetOperations
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> SetOperations<K, V> opsForSet() {
        return redisTemplate.opsForSet();
    }

    /**
     * 获取SetOperations
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> ZSetOperations<K, V> opsForZSet() {
        return redisTemplate.opsForZSet();
    }

    /**
     * 获取HashOperations
     *
     * @param <K>
     * @param <HK>
     * @param <HV>
     * @return
     */
    public static <K, HK, HV> HashOperations<K, HK, HV> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * key是否存在
     *
     * @param key
     * @return
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
     * @param classType 返回类型
     * @param <V>
     * @return
     */
    public static <V> V get(Object key, Class<V> classType) {
        Object value = redisTemplate.opsForValue().get(key);
        return (V) value;
    }

}
