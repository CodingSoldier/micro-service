package com.github.codingsoldier.example.gateway;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01FeignExceptionControllerTest extends BaseTest {

    @Test
    void circuitBreaker01() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/circuitBreaker01?name=MicroServiceException")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.code").isEqualTo(20000)
                .jsonPath("$.data.value").isEqualTo("circuitBreaker01进行降级");
    }

    @Test
    void circuitBreaker02Result() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/circuitBreaker02Result?name=MicroServiceException")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.code").isEqualTo(40000)
                .jsonPath("$.message").isEqualTo("circuitBreaker02Result进行降级");
    }

    @Test
    void circuitBreaker03Timeout() {
        // 显式设置超时（覆盖全局配置）
        webClient.mutate()
            .responseTimeout(Duration.ofSeconds(300))
            .build()
            .get()
            .uri("http://localhost:8000/cloud-web-01/feign01/exception/circuitBreaker03Timeout?timeout=100")
            .exchange()
            .expectStatus().isEqualTo(500)
            .expectBody()
            .jsonPath("$.code").isEqualTo(50000)
            .jsonPath("$.message").isEqualTo("circuitBreaker03Timeout服务降级超时");
    }

    @Test
    void bulkheadSemaphore01() throws InterruptedException {
        Thread.startVirtualThread(() -> {
            webClient.get()
                .uri("/cloud-web-01/feign01/exception/bulkhead/semaphore/01?timeout=10")
                .exchange();
        });
        Thread.startVirtualThread(() -> {
            webClient.get()
                .uri("/cloud-web-01/feign01/exception/bulkhead/semaphore/01?timeout=10")
                .exchange();
        });
        TimeUnit.MILLISECONDS.sleep(500);
        Thread.startVirtualThread(() -> {
            webClient.get()
                .uri("/cloud-web-01/feign01/exception/bulkhead/semaphore/01?timeout=-1")
                .exchange()
                .expectStatus().isEqualTo(500)
                .expectBody()
                .jsonPath("$.code").isEqualTo(50000)
                .jsonPath("$.message").isEqualTo("bulkheadSemaphore01人数太多，请稍后重试。");
        });
        TimeUnit.SECONDS.sleep(3);
    }

//    @Test
//    void bulkheadThreadPool01() throws InterruptedException {
//        Thread.startVirtualThread(() -> {
//            webClient.get()
//                .uri("/cloud-web-01/feign01/exception/bulkhead/threadPool/01?timeout=20")
//                .exchange();
//        });
//        Thread.startVirtualThread(() -> {
//            webClient.get()
//                .uri("/cloud-web-01/feign01/exception/bulkhead/threadPool/01?timeout=20")
//                .exchange();
//        });
//        TimeUnit.MILLISECONDS.sleep(500);
//        Thread.startVirtualThread(() -> {
//            webClient.get()
//                .uri("/cloud-web-01/feign01/exception/bulkhead/threadPool/01?timeout=-1")
//                .exchange()
//                .expectStatus().isEqualTo(200)
//                .expectBody()
//                .jsonPath("$.code").isEqualTo(40000)
//                .jsonPath("$.message").isEqualTo("bulkheadThreadPool01Fallback人数太多，请稍后重试");
//        });
//        TimeUnit.SECONDS.sleep(3);
//    }

    @Test
    void bulkheadRateLimiter01() throws InterruptedException {
        webClient.get()
            .uri("/cloud-web-01/feign01/exception/bulkhead/rateLimiter/01?name=是否限流")
            .exchange();
        webClient.get()
            .uri("/cloud-web-01/feign01/exception/bulkhead/rateLimiter/01?name=是否限流")
            .exchange();
        webClient.get()
            .uri("/cloud-web-01/feign01/exception/bulkhead/rateLimiter/01?name=是否限流")
            .exchange();
        webClient.get()
            .uri("/cloud-web-01/feign01/exception/bulkhead/rateLimiter/01?name=是否限流")
            .exchange()
            .expectStatus().isEqualTo(500)
            .expectBody()
            .jsonPath("$.code").isEqualTo(50000)
            .jsonPath("$.message").isEqualTo("bulkheadRateLimiter01Fallback人数太多，已经限流。");
    }

}