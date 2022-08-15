# starter-spring-doc

## 使用方式
引入依赖即可
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-spring-doc</artifactId>
</dependency>
```

## 访问地址

http://ip:port/${server.context-path}/swagger-ui/index.html

## 自定义配置项
```yaml
framework:
  starter:
    spring-doc:
      title: boot-web的API
```

## 补充
本模块只在 SpringDocConfig 中做了简单配置，引入本模块依赖后，您仍然可以在工程中对SpringDoc进行配置。
