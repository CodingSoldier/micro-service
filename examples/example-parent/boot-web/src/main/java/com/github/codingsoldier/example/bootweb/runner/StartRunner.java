package com.github.codingsoldier.example.bootweb.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartRunner implements CommandLineRunner {

    @Value("${profile-val:未配置}")
    private String profileVal;

    @Override
    public void run(String... args) throws Exception {
        log.info("###################profile-val={}", profileVal);
    }

}
