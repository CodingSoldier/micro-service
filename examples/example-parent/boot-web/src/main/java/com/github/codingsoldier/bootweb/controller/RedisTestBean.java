package com.github.codingsoldier.bootweb.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RedisTestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer age;
    private String name;
    private LocalDateTime updateTime;

}
