# micro-service-starters

常用框架的封装，尽量遵循“只做增强不做修改”的原则，大部分是对 spring-boot-starter 的二次封装、增强。

## 开发自定义starter

1、在 micro-service-starters 下新增一个工程，例如：starter-a

2、starter-a 的 parent 设置为 micro-service-starters

3、micro-service-starters 的 pom.xml 的 modules 加入 starter-a 模块

4、starter-a 开发完成后，将 starter-a 加入到 micro-service-dependencies 的 dependencyManagement 中
