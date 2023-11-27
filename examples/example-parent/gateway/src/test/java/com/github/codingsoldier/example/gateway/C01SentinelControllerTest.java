package com.github.codingsoldier.example.gateway;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import org.junit.jupiter.api.Test;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01SentinelControllerTest extends BaseTest {

    /**
     * cloud-web-01 必须连接上 sentinel
     */
    @Test
    void fromNacos() {
        String uri = "/cloud-web-01/sentinel/from-nacos";
        // 多发送几次，制造限流
        webClient.get()
                .uri(uri)
                .exchange();
        webClient.get()
                .uri(uri)
                .exchange();
        webClient.get()
                .uri(uri)
                .exchange();

        // 测试限流
        webClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.BAD_REQUEST.getCode())
                .jsonPath("$.message").isEqualTo("访问太频繁，请稍后重试。");
    }

}