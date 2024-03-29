package com.github.codingsoldier.example.gateway;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class C01FeignTestControllerTest extends BaseTest {

    @Test
    void respVoid() {
        webClient.get()
                .uri("/cloud-web-01/feign01/test/req/resp/void")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode());
    }

    @Test
    void reqPath() {
        webClient.get()
                .uri("/cloud-web-01/feign01/test/req/path/123456?name=")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("123456--");

        webClient.get()
                .uri("/cloud-web-01/feign01/test/req/path/123456?name=测试信息")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("123456--测试信息");
    }

    @Test
    void reqParam() {
        webClient.get()
                .uri("/cloud-web-01/feign01/test/req/resp/param?name=&pageNum=1&pageSize=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("--1--10");

        webClient.get()
                .uri("/cloud-web-01/feign01/test/req/resp/param?name=名字&pageNum=1&pageSize=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data").isEqualTo("名字--1--10");

    }

    @Test
    void nameList() {
        List<String> body = Arrays.asList("测试中文", "sdfsf");
        webClient.post()
                .uri("/cloud-web-01/feign01/test/req/resp/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data[0]").isEqualTo("测试中文");

    }

    @Test
    void reqRespMap() {
        HashMap<String, Object> body = new HashMap<>();
        webClient.post()
                .uri("/cloud-web-01/feign01/test/req/resp/map")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data.newKey").isEqualTo("web02返回");

        body.put("aa", "测试");
        webClient.post()
                .uri("/cloud-web-01/feign01/test/req/resp/map")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data.aa").isEqualTo("测试");
    }

    @Test
    void reqRespDemoVo() {
        String body = "{\"id\":123,\"name\":\"中文\",\"date\":\"2022-11-01 01:02:02\",\"localDate\":\"2012-01-10\",\"localDateTime\":\"2022-08-01 01:02:02\"}";
        webClient.post()
                .uri("/cloud-web-01/feign01/test/req/resp/demo-vo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data.date").isEqualTo("2022-11-01 01:02:02")
                .jsonPath("$.data.localDate").isEqualTo("2012-01-10")
                .jsonPath("$.data.localDateTime").isEqualTo("2022-08-01 01:02:02");
    }

    @Test
    void respResult() {
        webClient.get()
                .uri("/cloud-web-01/feign01/test/resp/result?name=中文")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo(ResultCodeEnum.SUCCESS.getCode())
                .jsonPath("$.data.localDateTime").isEqualTo("2010-10-10 01:02:10");
    }

}