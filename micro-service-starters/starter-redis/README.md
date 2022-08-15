# starter-redis

## 使用方式
引入依赖即可
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-redis</artifactId>
</dependency>
```
## 自定义配置项
```yaml
framework:
  starter:
    redis:
      # 是否启用starter-redis，默认true
      enabled: true
```

## 使用 RedisUtil
```java
    RedisUtil.<String, String>opsForValue().set("for-value:aa", "aaa");
    String str = RedisUtil.<String, String>opsForValue().get("for-value:aa");
    log.info("for-value:aa {}", str);

    RedisTestBeanDto redisTestBean = RedisTestBeanDto.builder()
            .id(11L).age(18).name("名字").updateTime(LocalDateTime.now())
            .build();
    RedisUtil.opsForValue().set("for-value:bean", redisTestBean);
    RedisTestBeanDto bean1 = RedisUtil.<String, RedisTestBeanDto>template().opsForValue().get("for-value:bean");
    log.info("获取值 {}", bean1);

    RedisUtil.<String, String, Integer>opsForHash().put("hashkey", "22", 45678);
    Integer hashVal = RedisUtil.<String, String, Integer>opsForHash().get("hashkey", "22");
    log.info("hashVal {}", hashVal);
```
