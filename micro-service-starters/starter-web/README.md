# starter-web
封装 spring-boot-starter-web 常用功能

## 使用方式
引入依赖即可
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-web</artifactId>
</dependency>
```

## 自定义配置项
```yaml
framework:
  starter:
    web:
      # 是否启用 starter-web，默认true     
      enabled: true
      # 是否启用 WebMvcConfig 配置类，默认true
      enableWebMvcConfig: true      
      logging:
        # 是否打印request、responseBody日志。默认false
        requestResponseLog: true
        # 是否打印request日志，是否打印request日志。默认false
        requestLog: true
        # 是否打印request日志。默认false
        responseBodyLog: true
        # 是否包含请求头信息。默认false
        includeHeaders: true
      # 企业微信消息
      work-weixin:
        # 是否启用企业微信
        enable: true
        # 企业微信机器人webhook url
        url: https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxxxxxxxxxxx
        # 信息最大长度，最多4000个字符
        contentMaxLength: 1500
        # 标题
        title: 标题
        # 标题颜色（只支持3种内置颜色） info 绿色 comment 灰色 warning 橙红色
        titleColor: comment
```

## open-feign、返回值包装器ResponseBodyAdvice、全局异常捕获器 整合，代码介绍
1、Controller 返回值被包装，与 feign 冲突
```java
// 服务提供方代码
@PostMapping(value = "/req/resp/demo-vo")
public DemoVo reqRespDemoVo(@RequestBody DemoVo demoVo) {
    return resp;
}
```
```java
// feign接口代码
@PostMapping(value = "/req/resp/demo-vo")
DemoVo reqRespDemoVo(@RequestBody DemoVo demoVo);
```
由于 ResponseBodyAdvice 对返回值进行包装，服务提供方实际返回类型是Result，与feign接口定义的返回值类型不同，导致远程调用得到的结果是null

解决方式：

每个feign请求都加上请求头 is-feign-request = true。代码
```java
FeignConfig.requestInterceptor()
```

ResponseBodyAdvice 判断请求头，如果带有 is-feign-request = true，不包装 Controller 返回值。代码：
```java
ResponseBodyWrapperAdvice#handleBody()
```

2、全局异常捕获导致的问题

服务提供方抛出异常，全局异常捕获器将 Controller 返回结果设置为 Result

解决方案：

（1）、FeignInterceptor.preHandle() 拦截请求，如果是feign请求，将 Controller 接口的返回值类型添加到 RequestAttributes 中

（2）、服务提供方处理返回值时，满足如下条件：
```
    1、请求是feign请求
    2、Controller返回值被全局异常捕获器处理过
    3、Controller 方法定义的返回值类型与实际返回值不一致
```
则设置 Response Status Code = HttpStatus.INTERNAL_SERVER_ERROR

代码位置 ResponseBodyWrapperAdvice#handleBody()

（3）、FeignErrorDecoder 实现了 ErrorDecoder 接口，当 Response Status Code 不在 200 ~ 299 范围内，抛出异常
```java
new FeignResultErrorException(result.getCode(), result.getMessage());
```
（4）、远程调用发起方的全局异常捕获器，捕获 FeignResultErrorException ，即可将下游服务的返回结果返回给前端。

（5）、feign fallback 降级处理问题。如果 feign 接口配置了 fallback，FeignErrorDecoder抛出异常，异常会被fallback处理。

由于 fallback，没有异常信息，不建议使用 fallback。

建议使用 fallbackFactory，并判断异常是否为 FeignResultErrorException、如果是，则直接抛出异常即可。代码例子：ExceptionFallbackFactory.create()

（6）、整个流程处理逻辑较为复杂，建议把服务提供方的 Controller 方法返回值定义为 Result。
```
ResponseBodyWrapperAdvice 会判断，如果返回值类型是Result，则不会再包装返回值；
并且全局异常捕获器返回的结果也是 Result。
```
就避免了（1）~（5）一大堆复杂处理逻辑。



