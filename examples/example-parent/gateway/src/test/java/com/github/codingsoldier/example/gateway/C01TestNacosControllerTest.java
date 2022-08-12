package com.github.codingsoldier.example.gateway;

import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01TestNacosControllerTest extends BaseTest{

    @Test
    void testHelloPredicates() {
        webClient.get()
            .uri("/cloud-web-01/test-nacos/val")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.data").isEqualTo("测试用例数据");
    }

}