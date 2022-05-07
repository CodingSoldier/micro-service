## micro-service 项目介绍
spring-cloud 微服务开发框架

micro-service 包含 micro-service-dependencies 子模块。

## 环境依赖
Java版本 = 1.8

Maven版本 >= 3.8.4

## 修改版本
1、修改 micro-service/pom.xml 中的 revision 。

2、运行 清理、安装或部署 命令。

mvn clean install -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

3、如果 pom.xml 的 <version>${revision}</version> 为红色，则刷新 maven 导入即可

## 安装/部署
### 安装
在 micro-service 目录下执行：

mvn clean

mvn install -D maven.test.skip=true

或者

mvn clean install -D maven.test.skip=true

### 部署
mvn clean

mvn deploy -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

## 使用 micro-service
### 方式一
1、新建 xx-parent 工程，在 dependencyManagement 中导入依赖
```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>com.github.codingsoldier</groupId>
                <artifactId>micro-service-dependencies</artifactId>
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
                <version>1.0.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
2、xx-parent 使用 micro-service 作为 parent。

这种方式会自动引入 maven-compiler-plugin、flatten-maven-plugin，同时 micro-service 在 pluginManagement 指定了 spring-boot-maven-plugin 的版本
```xml
    <parent>
        <groupId>com.github.codingsoldier</groupId>
        <artifactId>micro-service</artifactId>
        <version>1.0.0</version>
    </parent>
```

<br>

[example-parent](./examples/example-parent) 是使用例子，使用的是“方式一”

## 其他说明
examples目录是使用例子

middlewares-server目录是常用的中间件服务

