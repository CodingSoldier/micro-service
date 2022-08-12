package com.github.codingsoldier.example.gateway;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "20000")
class C01FeignTimeoutControllerTest{

    @Autowired
    protected WebTestClient webClient;

    @Test
    void timeout01FallbackFactory() {
        /**
         * 1、cloud-web-01 的feign超时时间设置为5000
         * 2、Web02FeignTimeoutClient 使用 fallbackFactory = Sentinel02TimeoutFallbackFactory.class
          */
        webClient.get()
                .uri("/cloud-web-01/feign01/test/timeout01?timeout=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResponseCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("调用web02出错，fallbackFactory降级处理。");
    }

    // @Test
    // void timeout01Fallback() {
    //     /**
    //      * 1、cloud-web-01 的feign超时时间设置为5000
    //      * 2、Web02FeignTimeoutClient 使用 fallback= Sentinel02TimeoutFallback.class
    //       */
    //     webClient.get()
    //             .uri("/cloud-web-01/feign01/test/timeout01?timeout=10")
    //             .exchange()
    //             .expectStatus().isOk()
    //             .expectBody()
    //             .jsonPath("$.code").isEqualTo(ResponseCodeEnum.SUCCESS.getCode())
    //             .jsonPath("$.data").isEqualTo("调用web02出错，fallback降级处理。");
    // }

}