package com.github.codingsoldier.example.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01ExcelTestControllerTest {

    @Test
    void download() throws Exception {
        WebTestClient.bindToServer().baseUrl("http://localhost:8000")
                .build()
                .get().uri("/cloud-web-01/excel/download")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType("application/vnd.ms-excel;charset=utf-8");
    }
}