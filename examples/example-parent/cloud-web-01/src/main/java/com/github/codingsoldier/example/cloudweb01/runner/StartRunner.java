package com.github.codingsoldier.example.cloudweb01.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartRunner implements CommandLineRunner {

    @Value("${test-nacos:null}")
    private String testNacos;


    @Override
    public void run(String... args) throws Exception {
        log.info("###################test-nacos={}", testNacos);
        log.info("@@@@@@@@@@@@@@@@@@2022-09-07#……………");
    }

}
