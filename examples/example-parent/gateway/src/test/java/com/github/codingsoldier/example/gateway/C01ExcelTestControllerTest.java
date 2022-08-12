package com.github.codingsoldier.example.gateway;

import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01ExcelTestControllerTest extends BaseTest{

    @Test
    void download() throws Exception {
        webClient.get()
            .uri("/cloud-web-01/excel/download")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType("application/vnd.ms-excel;charset=utf-8");
    }
}