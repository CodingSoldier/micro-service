// package com.github.codingsoldier.example.cloudweb01.traceid;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.scheduling.annotation.Scheduled;
//
// // 开启定时任务
// @EnableScheduling
// @Configuration
// @Slf4j
// public class MyTask {
//
//     // 每5秒执行一次
//     @Scheduled(cron="0/5 * * * * ?")
//     public void execute(){
//         log.info("@Scheduled 定时任务有traceId");
//     }
//
// }
