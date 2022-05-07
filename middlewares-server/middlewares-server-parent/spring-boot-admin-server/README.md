# spring-boot-admin 监控服务端

## 与 spring-boot 集成
1、pom.xml 导入依赖
```xml
    <dependency>
        <groupId>de.codecentric</groupId>
        <artifactId>spring-boot-admin-starter-client</artifactId>
        <version>2.4.2</version>
    </dependency>
```
2、修改配置文件
```yaml
spring:
  # springboot方式连接spring-boot-admin-server
  boot:
    admin:
      client:
        url: http://localhost:10100
```
spring-boot-admin 访问地址：http://localhost:10100/applications


## 与nacos整合。有bug，新服务需要重启spring-boot-admin-server才能被监听到
spring-boot-admin-server注册到nacos后 ，nacos的客户端不需要配置

spring.boot.admin.client.url=http://localhost:10100

只需要做以下操作

1、客户端注册到nacos，即可被 springbootadmin 监控

2、客户端如果配置了，server.port.servlet

application.yaml增加配置
```yaml
spring:
  cloud:
    nacos:
      discovery:
        metadata:
          management:
            context-path: /actuator
```


