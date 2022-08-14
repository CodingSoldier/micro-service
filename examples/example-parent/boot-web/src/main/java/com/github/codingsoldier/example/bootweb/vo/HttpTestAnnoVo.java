package com.github.codingsoldier.example.bootweb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.codingsoldier.example.bootweb.common.DateAllTestAnnoSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HttpTestAnnoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer age;
    private String name;

    /**
     *  @DateTimeFormat 时间格式化，主要是前后到后台的时间格式的转换
     *  @JsonFormat序列化器，后台到前台的时间格式的转换
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private LocalDate localDate;

    @JsonSerialize(using = DateAllTestAnnoSerializer.class)
    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;
}
