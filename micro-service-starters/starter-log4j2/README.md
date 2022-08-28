# starter-log4j2

### 不建议使用starter-log4j2，建议使用 starter-logback 
spring-boot-starter-test 默认使用了 logback ，要使用log4j2，spring-boot-starter-test 需要排除 spring-boot-starter-logging

## 使用方式
1、引入依赖
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-log4j2</artifactId>
</dependency>
<!-- 如果引入了spring-boot-starter-test，需要排除spring-boot-starter-logging-->
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-test</artifactId>
<scope>test</scope>
<exclusions>
    <exclusion>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </exclusion>
</exclusions>
</dependency>
```
2、指定 logging.config 为 classpath:log4j2-spring.xml


注意：如果使用了nacos作为配置中心，必须将此配置放到 <font color=red>bootstrap.yml</font> 中
```yaml
logging:
  # classpath:log4j2-spring.xml 已在 starter-log4j2 中配置好了，无需新建
  config: classpath:log4j2-spring.xml
```
3、本模块只提供一个 log4j2-spring.xml 日志配置文件，您依旧可以在application配置文件中使用spring提供的logging配置项

## 自定义配置项
```yaml
framework:
  starter:
    # starter-log4j2 配置
    log4j2:
      # 信息最大长度
      msg-max-length: 9000
      # 异常信息最大长度
      ex-max-length: 2000
      time-based-triggering-policy:
        # 多久滚动一次
        interval: 24
      size-based-triggering-policy:
        # 超过该大小就触发日志文件滚动更新
        size: 1MB
      default-rollover-strategy:
        # 同一文件夹下文件数量到达该数量开始覆盖
        max: 7
```

## 对 sleuth 的支持
前端每个http请求头带上x-req-trace-id。

x-req-trace-id由小写英文、数字组成，长度为16位，每次http请求的x-req-trace-id不相同。