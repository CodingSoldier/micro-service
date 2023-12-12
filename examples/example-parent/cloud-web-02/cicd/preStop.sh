#!/bin/sh
# nacos客户端实例下线
curl --location --request GET 'http://127.0.0.1:8002/cloud-web-02/nacos/graceful/deregister/instance'
# 睡眠一段时间，等待gateway网关将微服务剔除，不再接收流量
sleep 50
