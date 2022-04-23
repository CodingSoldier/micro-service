// package com.github.codingsoldier.example.cloudweb02;
//
// import org.springframework.beans.factory.BeanFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
// import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
// import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
// import javax.annotation.PostConstruct;
// import java.util.concurrent.Executor;
//
// // @Configuration
// // @EnableAsync
// public class ThreadConfig extends AsyncConfigurerSupport {
//
//     public static Executor myExecutor;
//
//     @Autowired
//     private BeanFactory beanFactory;
//
//     @Override
//     public Executor getAsyncExecutor() {
//         ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//         threadPoolTaskExecutor.setCorePoolSize(10);
//         threadPoolTaskExecutor.setMaxPoolSize(20);
//         threadPoolTaskExecutor.initialize();
//
//         return new LazyTraceExecutor(beanFactory, threadPoolTaskExecutor);
//     }
//
//     @PostConstruct
//     public void init(){
//         myExecutor = getAsyncExecutor();
//     }
//
// }