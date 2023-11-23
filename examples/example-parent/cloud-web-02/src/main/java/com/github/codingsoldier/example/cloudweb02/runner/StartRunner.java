package com.github.codingsoldier.example.cloudweb02.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartRunner implements CommandLineRunner {

    @Value("${test.val:未配置}")
    private String testVal;

    @Override
    public void run(String... args) throws Exception {
        log.info("###################test.val={}", testVal);
    }

}
