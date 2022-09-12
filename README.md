## micro-service
spring-cloud 微服务开发框架

## 环境依赖
Java版本 = 1.8

Maven版本 >= 3.8.4

## 项目介绍

### micro-service
父工程

### micro-service-dependencies
依赖模块，包含 micro-service-common、micro-service-starters 子模块

所有的依赖都应该在本模块导入

[更多介绍](./micro-service-dependencies/README.md)

### micro-service-common
公共模块

公共模块不允许依赖其他子模块

[更多介绍](./micro-service-common/README.md)

### micro-service-starters
常用框架的封装。大部分是对 spring-boot-starter 的二次封装、增强。

[更多介绍](./micro-service-starters/README.md)

## 修改版本
1、修改 micro-service/pom.xml 中的 revision

2、运行 清理、安装或部署 命令

mvn clean install -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

3、如果 pom.xml 的 <version>${revision}</version> 为红色，则刷新 maven 导入即可

## 使用 micro-service

1、新建 xx-parent 工程。pom.xml 配置 parent 为 micro-service
```xml
    <parent>
        <groupId>com.github.codingsoldier</groupId>
        <artifactId>micro-service</artifactId>
        <!-- 改为最新版本 -->
        <version>1.0.0</version>
    </parent>
```
2、在 dependencyManagement 中导入依赖
```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.github.codingsoldier</groupId>
                <artifactId>micro-service-dependencies</artifactId>
                <!-- 改为最新版本 -->
                <version>1.0.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

这种方式会自动引入 maven-compiler-plugin、flatten-maven-plugin 插件

同时 micro-service 在 pluginManagement 指定了 spring-boot-maven-plugin
的版本

3、demo地址 [example-parent](./examples/example-parent)

## 使用jenkins打包、部署
[地址](https://gitee.com/CodingSoldier/cicd)

## 其他说明

[examples](./examples) 目录是使用例子

[middlewares-server](./middlewares-server) 目录是常用的中间件服务

spring-cloud-alibaba、spring-cloud版本关系 https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

