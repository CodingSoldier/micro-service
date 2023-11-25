# starter-mybatis-plus

## 使用方式
引入依赖即可
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-mybatis-plus</artifactId>
</dependency>
```

## 自定义配置项
```yaml
micro-service:
  starter:
    mybatis-plus:
      # 是否启用 starter-mybatis-plus，默认true
      enabled: true
```

## 代码生成
使用方式请查看[MybatisPlusCodeGeneratorTest.java](./src/test/java/com/github/codingsoldier/starter/mybatisplus/generator/MybatisPlusCodeGeneratorTest.java)

## 自动填充功能
1、实现 TokenInterface 接口

2、表插入数据是会自动填充 CustomMetaObjectHandler#CREATE_BY、CustomMetaObjectHandler#UPDATE_BY 字段。

 更新时会自动填充 CustomMetaObjectHandler#UPDATE_BY 字段。
