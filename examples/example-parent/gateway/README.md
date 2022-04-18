# gateway 网关服务

## 动态路由
1、结合nacos实现动态路由

2、nacos新建动态路由配置 gateway-dynamic-router-json ，类型为json

[gateway-dynamic-router-json配置](./src/main/resources/nacos配置/gateway-dynamic-router-json.json)

指定动态路由配置json
```json
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

