# starter-logback
logback配置

## 使用方式
1、引入依赖
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-logback</artifactId>
</dependency>
```
2、指定 logging.config 为 classpath:logback-spring.xml
```yaml
logging:
  # logback-spring.xml 已在 starter-logback 中配置好了，无需新建
  config: classpath:logback-spring.xml
```
3、本模块只提供一个 logback-spring.xml 日志配置文件，您依旧可以在application配置文件中使用spring提供的logging配置项

## 自定义配置项
```yaml
micro-service:
  starter:
    logback:
      # 信息最大长度
      msg-max-length: 9000
      # 异常信息最大长度
      ex-max-length: 2000
```

## 对链路追踪的支持
请查看[starter-micrometer-tracing](../starter-micrometer-tracing/README.md)
