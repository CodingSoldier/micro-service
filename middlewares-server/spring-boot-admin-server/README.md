# spring-boot-admin 监控服务端
访问地址：http://localhost:10100/applications

## 与nacos整合。有bug，新服务需要重启spring-boot-admin-server才能被监听到
spring-boot-admin-server注册到nacos后 ，nacos的客户端不需要配置

spring.boot.admin.client.url=http://localhost:10100

只需要做以下操作

1、客户端注册到nacos，即可被 springbootadmin 监控

2、客户端如果配置了，server.port.servlet

application.yaml增加配置
```json
spring:
  cloud:
    nacos:
      discovery:
        metadata:
          management:
            context-path: /actuator
```


