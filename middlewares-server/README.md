# middlewares-server
middlewares-server 放置常用中间件服务，可以启动运行。

# 常用中间件服务
## nacos
安装 nacos

https://nacos.io/zh-cn/docs/quick-start.html

## spring-boot-admin-server 
[更多说明](./middlewares-server-parent/spring-boot-admin-server/README.md)

## zipkin-server
1、下载 zipkin-server

https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/2.12.9/zipkin-server-2.12.9-exec.jar

2、使用 mysql 持久化

    创建数据库 zipkin，字符集=utf8 ，排序规则=utf8_general_ci

    执行初始化 SQL，地址：https://github.com/openzipkin/zipkin/blob/master/zipkin-storage/mysql-v1/src/main/resources/mysql.sql 

3、启动 zipkin-server-2.12.9-exec.jar 。服务访问地址：http://localhost:10103/zipkin

    java -Xmx512m -Xms512m -jar zipkin-server-2.12.9-exec.jar --STORAGE_TYPE=mysql --MYSQL_HOST=127.0.0.1 --MYSQL_TCP_PORT=3306 --MYSQL_USER=root --MYSQL_PASS=cpq..123 --MYSQL_DB=zipkin --QUERY_PORT=10103

## hystrix-dashboard
[更多说明](./middlewares-server-parent/hystrix-dashboard/README.md)
