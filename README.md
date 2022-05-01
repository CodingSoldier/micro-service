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

## 其他说明
examples目录是使用例子

middlewares-server目录是常用的中间件服务

