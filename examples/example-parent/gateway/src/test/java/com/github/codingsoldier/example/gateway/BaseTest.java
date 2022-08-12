package com.github.codingsoldier.example.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
@SpringBootTest
@AutoConfigureWebTestClient
class BaseTest {

    @Autowired
    protected WebTestClient webClient;

}