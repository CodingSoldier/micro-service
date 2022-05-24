package com.github.codingsoldier.example.cloudweb01.feign.type;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DemoVo {
    private Long id;
    private String name;
    private LocalDateTime startTime;
}
