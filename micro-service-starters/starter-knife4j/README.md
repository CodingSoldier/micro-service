# starter-knife4j

## 使用方式
引入依赖即可
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-knife4j</artifactId>
</dependency>
```

## 自定义配置项
```yaml
micro-service:
  starter:
    knife4j:
      # 是否启用 starter-knife4j，默认是true
      enabled: true
#      # 标题
#      title: boot-web的API
```

## 访问地址

http://ip:port/${server.context-path}/doc.html#/home

## 补充
本模块只在 Knife4jConfiguration 中做了简单配置，引入本模块依赖后，您仍然可以在工程中对knife4j进行配置。
