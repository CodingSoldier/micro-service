# starter-sleuth

## 使用方式
1、引入依赖
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-sleuth</artifactId>
</dependency>
```

【可选项】 2、 前端每个http请求头带上x-req-trace-id
，x-req-trace-id由小写英文、数字组成，长度为16位，每次http请求的x-req-trace-id不相同

引入依赖 starter-logback（建议）或者 starter-log4j2

## 与 spring-cloud-sleuth-zipkin 整合
1、导入依赖
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-sleuth-zipkin</artifactId>
</dependency>
```

## 自定义配置项
```yaml
framework:
  starter:
    sleuth:
      # 是否启用starter-sleuth，默认true
      enabled: true
```

2、application.yml配置
```yaml
spring:
  # sleuth + zipkin 分布式链路追踪
  sleuth:
    sampler:
      # ProbabilityBasedSampler 抽样策略
      probability: 1.0  # 采样比例, 1.0 表示 100%, 默认是 0.1
      # RateLimitingSampler 抽样策略, 设置了限速采集, spring.sleuth.sampler.probability 属性值无效
      rate: 100  # 每秒间隔接受的 trace 量
  zipkin:
    sender:
      # 指定通过什么类型发送消息 默认是 web
      type: WEB
    # 指定 zipkin 的地址
    base-url: http://localhost:10103
```

## 带trace信息的线程池工具类
使用新线程运行任务，日志没有trace信息，无法实现链路追踪
，本模块提供两个带trace信息的线程池工具类

带trace信息的线程池工具类：ThreadPoolTraceUtil 

带trace信息的线程池Callable工具类：TaskTraceUtil 

