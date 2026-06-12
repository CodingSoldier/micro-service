# AGENTS.md

This file provides guidance to Codex (Codex.ai/code) when working with code in this repository.

## Project Overview

Spring Cloud 微服务开发框架，提供统一的依赖管理和常用框架封装。

### Version Stack

- JDK >= 25
- Maven >= 3.9.9
- Spring Boot 4.0.6
- Spring Cloud 2025.1.0
- Spring Cloud Alibaba 2025.1.0.0

## Module Architecture

```
micro-service (父工程，版本管理)
├── micro-service-dependencies (BOM，集中管理第三方依赖)
├── micro-service-common (公共模块，轻量级工具类、常量、异常处理等)
└── micro-service-starters (自定义 starters，对 spring-boot-starter 增强封装)
```

### Module Rules

1. **micro-service-common**: 不允许依赖其他子模块，不建议引入 Spring 等重型依赖
2. **micro-service-starters**: 遵循"只做增强不做修改"原则
3. **micro-service-dependencies**: 纯 BOM 模块，不包含代码

## Build Commands

### 本地安装（跳过测试）
```bash
mvn clean install -D maven.test.skip=true
```

### 部署到私有仓库（跳过测试）
```bash
mvn clean deploy -D maven.test.skip=true
```

### 版本管理

版本统一通过 `revision` 变量管理，配合 `flatten-maven-plugin` 实现占位符替换：
- 修改 `micro-service/pom.xml` 中的 `<revision>` 属性
- 运行构建命令后，`.flattened-pom.xml` 会被自动更新
- 如果 IDE 中 `<version>${revision}</version>` 显示红色，刷新 Maven 导入即可

## Example Reference

参考 `examples/example-parent` 了解框架使用方式，包含以下示例模块：
- `boot-web`: 单体 Web 应用示例
- `cloud-web-api`: API 模块
- `cloud-web-01/02`: 微服务示例
- `gateway`: Spring Cloud Gateway 示例

## Common Starters

- `starter-logback`: Logback 日志配置
- `starter-log4j2`: Log4j2 日志配置
- `starter-mybatis-plus`: MyBatis Plus 增强配置
- `starter-redis`: Redis 集成
- `starter-nacos`: Nacos 配置中心和服务发现
- `starter-openfeign`: OpenFeign 调用
- `starter-web`: Web 模块增强

## 开发自定义 Starter

1. 在 `micro-service-starters` 下新建工程（如 `starter-a`）
2. `starter-a` 的 parent 设置为 `micro-service-starters`
3. 在 `micro-service-starters/pom.xml` 的 `<modules>` 中加入新模块
4. 开发完成后，将新模块加入 `micro-service/pom.xml` 的 `<dependencyManagement>` 中
