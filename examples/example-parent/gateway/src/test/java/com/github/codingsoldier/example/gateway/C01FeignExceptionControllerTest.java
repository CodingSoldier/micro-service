package com.github.codingsoldier.example.gateway;

import java.time.Duration;
import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01FeignExceptionControllerTest extends BaseTest {

    @Test
    void name() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/name?name=MicroServiceException")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.code").isEqualTo(20000)
                .jsonPath("$.data.value").isEqualTo("name进行降级");
    }

    @Test
    void resultTypeNotChange() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/result/type/not/change?name=MicroServiceException")
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody()
                .jsonPath("$.code").isEqualTo(40000)
                .jsonPath("$.message").isEqualTo("resultTypeNotChange进行降级");
    }

    @Test
    void timeout01() {
        // 显式设置超时（覆盖全局配置）
        webClient.mutate()
            .responseTimeout(Duration.ofSeconds(300))
            .build()
            .get()
            .uri("http://localhost:8000/cloud-web-01/feign01/exception/timeout01?timeout=10")
            .exchange()
            .expectStatus().isEqualTo(500)
            .expectBody()
            .jsonPath("$.code").isEqualTo(50000)
            .jsonPath("$.message").isEqualTo("timeout01服务降级超时");
    }

}