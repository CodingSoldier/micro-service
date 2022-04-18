# cloud-web-01
spring-cloud工程使用例子

使用步骤：

1、添加 nacos-config、 nacos-discovery 依赖

2、nacos-config 、logging 配置必须写在 bootstrap.yaml 中

3、nacos-discovery 配置写在 application.yaml 中

4、启动类添加 @EnableDiscoveryClient

## Sleuth + Zipkin 分布式日志追踪
1、下载 zipkin-server 

https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/2.12.9/zipkin-server-2.12.9-exec.jar

2、使用 mysql 持久化

    创建数据库 zipkin，字符集=utf8 ，排序规则=utf8_general_ci

    执行初始化 SQL，地址：https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql 

3、启动 zipkin-server-2.12.9-exec.jar 。服务访问地址：http://localhost:9411/zipkin

    java -jar zipkin-server-2.12.9-exec.jar --STORAGE_TYPE=mysql --MYSQL_HOST=127.0.0.1 --MYSQL_TCP_PORT=3306 --MYSQL_USER=root --MYSQL_PASS=密码 --MYSQL_DB=zipkin

4、工程添加依赖
```xml
    <!-- zipkin = spring-cloud-starter-sleuth + spring-cloud-sleuth-zipkin-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
```
5、修改配置文件
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
    base-url: http://localhost:9411/
```
6、配置日志的xml添加 [%X{traceId},%X{spanId}]

7、运行项目，发送请求。

## openfeign
1、导入依赖包
```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!-- feign 替换 JDK 默认的 URLConnection 为 okhttp -->
    <dependency>
        <groupId>io.github.openfeign</groupId>
        <artifactId>feign-okhttp</artifactId>
    </dependency>
```
2、添加配置
```yxml
# Feign 的相关配置
feign:
  # feign 开启 gzip 压缩
  compression:
    # 对请求进行压缩
    request:
      enabled: true
      # 针对以下类型的数据进行压缩
      mime-types: text/xml,application/xml,application/json
      # 大于1M的数据才进行压缩
      min-request-size: 1024
    # 响应时进行压缩
    response:
      enabled: true
  # 禁用默认的 http
  httpclient:
    enabled: false
  # 启用 okhttp
  okhttp:
    enabled: true
```
3、启动类添加 @EnableFeignClients("com.github.codingsoldier.example")

4、添加配置类 FeignConfig、FeignOkHttpConfig

5、配置级别日志 org: debug ，控制台打印

    Sending a request via tracing feign client [org.springframework.cloud.sleuth.instrument.web.client.feign.TracingFeignClient@16a13a30] and the delegate [feign.okhttp.OkHttpClient@742d5808]

表明 okhttp 生效

