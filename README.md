## micro-service

spring-cloud 微服务开发框架

## 环境依赖

Java版本 = 1.8

Maven版本 >= 3.8.4

## 项目介绍

micro-service 包含 micro-service-dependencies 子模块。

### micro-service-dependencies

micro-service-dependencies 是依赖模块，包含 micro-service-common、micro-service-starters 子模块

[更多介绍](./micro-service-dependencies/README.md)

### micro-service-common

公共模块

micro-service-common 不允许依赖 micro-service-dependencies 任何的子模块。

[更多介绍](./micro-service-common/README.md)

### micro-service-starters

常用框架的封装。大部分是对 spring-boot-starter 的二次封装、增强。

[更多介绍](./micro-service-starters/README.md)

## 补充

版本关系 https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

## 修改版本

1、修改 micro-service/pom.xml 中的 revision

2、运行 清理、安装或部署 命令

mvn clean install -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

3、如果 pom.xml 的 <version>${revision}</version> 为红色，则刷新 maven 导入即可

## 使用 micro-service

### 方式一

1、新建 xx-parent 工程，在 dependencyManagement 中导入依赖

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

2、自定义 pluginManagement、plugins

### 方式二

1、新建 xx-parent 工程，在 dependencyManagement 中导入依赖

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

2、xx-parent 使用 micro-service 作为 parent。

```xml
    <parent>
        <groupId>com.github.codingsoldier</groupId>
        <artifactId>micro-service</artifactId>
        <!-- 改为最新版本 -->
        <version>1.0.0</version>
    </parent>
```

这种方式会自动引入 maven-compiler-plugin、flatten-maven-plugin 插件，同时 micro-service 在 pluginManagement 指定了 spring-boot-maven-plugin
的版本

<br>

### 使用建议

1、建议使用“方式一”，Aliyun Java Initializr 自动生成的代码也是没有 parent 的。

2、由于建议使用“方式一”， micro-service 没有创建 micro-service-parent 模块。

3、详细的使用方式请查看 [example-parent](./examples/example-parent)

## 其他说明

[examples](./examples) 目录是使用例子

[middlewares-server](./middlewares-server) 目录是常用的中间件服务


