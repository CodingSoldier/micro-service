package com.github.codingsoldier.example.bootweb.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RedisTestBeanDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;
  private Integer age;
  private String name;
  private LocalDateTime updateTime;

}
