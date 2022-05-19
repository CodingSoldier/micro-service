// package com.github.codingsoldier.example.cloudweb02;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
//
//
// @EnableScheduling //开启定时任务
// @Component
// @Slf4j
// public class MyTask {
//
//     @Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次
//     public void execute(){
//         log.info("打印日志，测试traceId");
//     }
//
// }
