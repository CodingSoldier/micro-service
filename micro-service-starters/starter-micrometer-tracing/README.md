# starter-micrometer-tracing

## 使用方式
1、引入依赖
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-micrometer-tracing</artifactId>
</dependency>
```
 2、前端每个http请求头带上x-req-trace-id，每次http请求的x-req-trace-id不相同
。x-req-trace-id由小写英文、数字组成，长度为16位

3、 引入依赖 starter-logback（建议）或者 starter-log4j2
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-logback</artifactId>
</dependency>
```
如果不引用starter-logback、starter-log4j2依赖，则需要在日志的pattern中加入 %X{x-req-trace-id:-},%X{traceId:-},%X{spanId:-}

例如：
```xml
<pattern>${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} [%X{x-req-trace-id:-},%X{traceId:-},%X{spanId:-}] ${PID:- } --- [%t] %-40.40logger{39} :  %.-${msgMaxLength}m%n${LOG_EXCEPTION_CONVERSION_WORD:-%.-${exMaxLength}wEx}}</pattern>

```

## 自定义配置项
```yaml
micro-service:
  starter:
    micrometer-tracing:
      # 是否启用micrometer-tracing，默认true
      enabled: true
```

## 带trace信息的线程池工具类
使用新线程运行任务，日志没有trace信息，无法实现链路追踪
，本模块提供1个带trace信息的线程池工具类TheadPoolTraceUtil


