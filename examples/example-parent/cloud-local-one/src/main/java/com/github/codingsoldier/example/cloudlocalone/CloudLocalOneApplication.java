package com.github.codingsoldier.example.cloudlocalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.codingsoldier.example"})
public class CloudLocalOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudLocalOneApplication.class, args);
    }

}
