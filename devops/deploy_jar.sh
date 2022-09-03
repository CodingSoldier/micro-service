#!/bin/bash

jar_name=$1
sleep_time=$2

echo "【信息】jar包名称 $jar_name"

# 必须先将project_pid定义为""，不然得到多个
project_pid=""
project_pid=$(ps -ef | grep $jar_name.jar | grep java | awk '{print $2}')
echo "【信息】进程号 $project_pid"

# kill进程
if [ -n "$project_pid" ]; then
    sudo kill -15 $project_pid
fi

sleep $sleep_time

project_pid=$(ps -ef | grep $jar_name.jar | grep java | awk '{print $2}')
if [ -n "$project_pid" ]; then
    sudo kill -9 $project_pid
fi

# 备份
mkdir -p backup
sudo cp -f $jar_name.jar backup/$jar_name.jar.`date '+%Y%m%d_%H.%M.%S'`

# 启动
{{jarStartCommand}}
