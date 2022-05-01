// package com.github.codingsoldier.example.cloudweb02.hystrix;
//
// import feign.hystrix.FallbackFactory;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.stereotype.Component;
//
// /**
//  * <h1>OpenFeign 集成 Hystrix 的另一种模式</h1>
//  */
// @Slf4j
// @Component
// public class HystrixClientFallbackFactory
//         implements FallbackFactory<HystrixClient> {
//
//     @Override
//     public HystrixClient create(Throwable throwable) {
//         log.error("feign调用异常，", throwable);
//         return new HystrixClient() {
//             @Override
//             public String test01(String name) {
//                 return "熔断返回信息";
//             }
//         };
//     }
// }
