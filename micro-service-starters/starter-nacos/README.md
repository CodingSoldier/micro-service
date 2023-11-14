# starter-nacos
1、当前的starter-nacos仅解决使用k8s部署微服务时，微服务优雅上下线的问题。如果不使用k8s部署微服务，不建议使用starter-nacos。

2、和其他starter一样，starter-nacos遵循只对nacos做增强不做修改的原则。

## starter-nacos实现nacos客户端优雅上线下线的方式
1、设置 spring.cloud.nacos.discovery.register-enabled=false，让nacos客户端仅订阅不注册。

2、在服务启动完成后，再将服务注册到nacos服务端。代码位置：NacosRegisterRunner.run()

3、微服务下线前，调用NacosGracefulController.deregisterInstance() 接口让nacos客户端实例下线。NacosApiInterceptor拦截器会拦截请求，校验请求发送者的IP是否在白名单内。

4、睡眠一段时间，等待gateway网关将微服务剔除，不再接收流量。

## 使用方式
1、微服务引入依赖
```xml
<dependency>
    <groupId>com.github.codingsoldier</groupId>
    <artifactId>starter-nacos</artifactId>
</dependency>
```
2、微服务的配置文件不能设置spring.cloud.nacos.discovery.register-enabled=true。否则会覆盖[nacos.properties](./src/main/resources/nacos.properties)中的配置项spring.cloud.nacos.discovery.register-enabled=false

3、新建preStop.sh脚本，将preStop.sh添加到docker镜像中。

以cloud-web-01为例子，脚本内容如下：
```shell
#!/bin/bash
# nacos客户端实例下线
curl --location --request GET 'http://127.0.0.1:8001/cloud-web-01/nacos/graceful/deregister/instance'
# 睡眠一段时间，等待gateway网关将微服务剔除，不再接收流量
sleep 20
```
详细使用方式可参考[cloud-web-01的cicd](../../examples/example-parent/cloud-web-01/cicd)

3、k8s部署文件添加preStop指令，执行preStop.sh脚本。

以cloud-web-02为例，配置方式如下：
```yaml
      lifecycle:
        preStop:
          exec:
            command:
              - /bin/sh
              - '-c'
              - /app/preStop.sh
```
详细使用方式可参考cloud-web-02的k8s部署文件[k8s-deploy-dev.yaml](../../examples/example-parent/cloud-web-02/cicd)

## 自定义配置项
```yaml
micro-service:
  starter:
    nacos:
      graceful:
        # 是否启用nacos客户端优雅上下线，默认true
        enabled: true
        # 可以调用nacos客户端下线接口的ip白名单列表，支持正则表达式，默认值=["127.0.0.1", "0:0:0:0:0:0:0:1"]
        ip-white-list:
          - 127.0.0.1
          - 0:0:0:0:0:0:0:1
        # nacos客户端在系统启动后，延迟注册的时间，默认值=1000
        delay-register-milliseconds: 1000
```
