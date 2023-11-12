package com.github.codingsoldier.example.gateway;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01TraceControllerTest extends BaseTest {

    @Test
    void testTraceId() {
        webClient.get()
                .uri("/cloud-web-01/trace/testTraceId")
                .header("x-req-trace-id", "43325268ff7aaaaa")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("web01-header-43325268ff7aaaaa-mdc-43325268ff7aaaaa-web02-header-43325268ff7aaaaa-mdc-43325268ff7aaaaa");
    }

    @Test
    void reqPath() {
        webClient.get()
                .uri("/cloud-web-01/trace/asyncAnno")
                .header("x-req-trace-id", "43325268ff7aaaaa")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("web01-header-43325268ff7aaaaa-mdc-43325268ff7aaaaa-web02-header-43325268ff7aaaaa-mdc-43325268ff7aaaaa");
    }

}