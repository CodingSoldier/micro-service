# gateway 网关服务

## 动态路由

1、结合nacos实现动态路由

2、nacos新建动态路由配置 gateway-dynamic-router-json ，类型为json

[gateway-dynamic-router-json配置](src/main/resources/nacos配置/gateway-dynamic-router-json.json)

指定动态路由配置json

```yaml
nacos:
  gateway:
    route:
      config:
        data-id: gateway-dynamic-router-json
        group: DEFAULT_GROUP
```

## 局部过滤器

1、创建 Part01Filter

Part01GatewayFilter implements GatewayFilter, Ordered

2、创建 Part01GatewayFilterFactory

```java
    @Override
    public GatewayFilter apply(Object config) {
        return new Part01GatewayFilter();
    }
```

3、spring 约定过滤器类名"xxx"+GatewayFilterFactory，所以filter名称为 Part01 ，将 Path01 填入路由中

```json
    "filters": [
        {
            "name": "Part01"
        }
    ]
```

4、过滤器只对 "id": "cloud-web-02" 生效

## 全局过滤器

实现 GlobalFilter，并加上 @Component 即可

```java
@Component
public class Global01Filter implements GlobalFilter {
    
}
```

## gateway 整合 sentinel、nacos

1、导入依赖

```xml
        <!-- 集成 Sentinel, 在网关层面实现限流 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
        </dependency>
        <!-- Sentinel 使用 Nacos 存储规则 -->
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
```

2、配置文件

```yaml
spring:
  cloud:
    sentinel:
      eager: true
      transport:
        port: 10112
        dashboard: 127.0.0.1:10102
      datasource:
        # 集成 Nacos
        ds1:
          nacos:
            server-addr: ${spring.cloud.nacos.discovery.server-addr}
            namespace: ${spring.cloud.nacos.discovery.namespace}
            data-id: gateway-flow-rule-sentinel
            group-id: DEFAULT_GROUP
            data-type: json
            rule-type: gw-flow
        ds2:
          nacos:
            server-addr: ${spring.cloud.nacos.discovery.server-addr}
            namespace: ${spring.cloud.nacos.discovery.namespace}
            data-id: gateway-flow-rule-api-sentinel
            group-id: DEFAULT_GROUP
            data-type: json
            rule-type: gw-api-group
```

3、nacos 新建json配置

dataId = gateway-flow-rule-sentinel

[json内容](gateway-flow-rule-sentinel.json)

dataId = gateway-flow-rule-api-sentinel

[json内容](gateway-flow-rule-api-sentinel.json)

4、新建 com.github.codingsoldier.example.gateway.sentinel.SentinelGatewayConfiguration

5、sentinel-server 启动参数多加 -Dcsp.sentinel.app.type=1

    java -Dserver.port=10102 -Dcsp.sentinel.app.type=1 -Dcsp.sentinel.dashboard.server=localhost:10102 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.4.jar

6、有 bug ,网关限流后会导致微服务的sentinel规则失效。

## gateway 整合 springdoc
1、导入依赖

```xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-webflux-ui</artifactId>
        <version>1.6.9</version>
    </dependency>
```

2、配置文件

```yaml
# springdoc配置
springdoc:
  # 预加载
  pre-loading-enabled: true
  swagger-ui:
    # 折叠接口
    doc-expansion: none
    urls:
      - name: 服务01
        url: /cloud-web-01/v3/api-docs
      - name: 服务02
        url: /cloud-web-02/v3/api-docs

```
3、新建 SpringDocConfig.java

4、访问地址

http://localhost:8000/webjars/swagger-ui/index.html