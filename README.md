# micro-service
spring-cloud 微服务开发框架
## 环境依赖
Java版本 = 1.8

Maven版本 >= 3.8.4

## 项目介绍
micro-service 是框架的父级工程。包含 micro-service-dependencies 子模块。

## 模块介绍
### common
公共包。

common 不允许依赖 framework-parent 任何的子模块。

[更多介绍](./common/README.md)

### starters
常用框架的封装。大部分是对 spring-boot-starter 的二次封装、增强。

[更多介绍](./starters/README.md)

### <font color=red>framework-dependencies 工程特别说明：</font>
1、framework-dependencies虽然在framework-parent目录下，但是framework-dependencies不是framework-parent的子模块。

2、framework-dependencies项目用于定义依赖，所有的依赖都应在framework-dependencies中定义。

3、framework-dependencies的版本单独定义，不受framework-parent控制。

[更多介绍](./framework-dependencies/README.md)

### middlewares-server
1、middlewares-server 放置常用中间件服务，可以启动运行。

2、middlewares-server 不是 framework-parent 的子模块

[更多介绍](./middlewares-server/README.md)

## 修改版本
1、修改 framework-parent/pom.xml 中的 revision 。

2、运行 清理、安装或部署 命令。

mvn clean install -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

3、如果 pom.xml 的 <version>${revision}</version> 为红色，则刷新 maven 导入即可

## 安装/部署
### 安装
在 framework-parent 目录下执行：

mvn clean

mvn install -D maven.test.skip=true

或者

mvn clean install -D maven.test.skip=true

### 部署
mvn clean

mvn deploy -D maven.test.skip=true

或者

mvn clean deploy -D maven.test.skip=true

## 使用 framework-parent 的 demo
https://github.com/CodingSoldier/example-parent
