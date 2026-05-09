# boot-web

spring-cloud工程使用例子

使用步骤：

1、添加 nacos-config、 nacos-discovery 依赖

2、nacos-config、logging 建议统一写在 application.yaml 中，并通过 `spring.config.import` 引入 Nacos 配置

3、nacos-discovery 配置写在 application.yaml 中

4、启动类添加 @EnableDiscoveryClient
