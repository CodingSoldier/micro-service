package com.github.codingsoldier.example.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01TestNacosControllerTest {

    @Test
    void testNacosVal() throws Exception {
        WebTestClient.bindToServer().baseUrl("http://localhost:8000")
            .build()
            .get().uri("/cloud-web-01/test-nacos/val")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.data").isEqualTo("测试用例数据");
    }
}