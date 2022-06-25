# starter-sleuth
## 与 spring-cloud-sleuth-zipkin 整合
1、导入依赖
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-sleuth-zipkin</artifactId>
        </dependency>
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



