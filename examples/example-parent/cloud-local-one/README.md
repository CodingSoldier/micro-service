# boot-web
spring-cloud工程使用例子

使用步骤：

1、添加 nacos-config、 nacos-discovery 依赖

2、nacos-config 、logging 配置必须写在 bootstrap.yaml 中

3、nacos-discovery 配置写在 application.yaml 中

4、启动类添加 @EnableDiscoveryClient
