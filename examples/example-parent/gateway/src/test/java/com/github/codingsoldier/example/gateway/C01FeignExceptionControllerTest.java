package com.github.codingsoldier.example.gateway;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01FeignExceptionControllerTest extends BaseTest {

    @Test
    void name() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/name?name=AppException")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResponseCodeEnum.BAD_REQUEST.getCode())
                .jsonPath("$.message").isEqualTo("测试APP异常");
    }

    @Test
    void hasFallbackName() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/has/fallback/name?name=AppException")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResponseCodeEnum.BAD_REQUEST.getCode())
                .jsonPath("$.message").isEqualTo("测试APP异常");
    }

    @Test
    void resultTypeNotChangeFallback() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/result/type/not/change/fallback?name=AppException")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(40020)
                .jsonPath("$.message").isEqualTo("resultTypeNotChange-测试APP异常");
    }

    @Test
    void resultTypeNotChange() {
        webClient.get()
                .uri("/cloud-web-01/feign01/exception/result/type/not/change?name=AppException")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(40020)
                .jsonPath("$.message").isEqualTo("resultTypeNotChange-测试APP异常");
    }

}