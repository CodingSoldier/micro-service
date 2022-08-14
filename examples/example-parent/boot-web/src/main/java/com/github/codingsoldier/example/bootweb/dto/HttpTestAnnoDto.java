package com.github.codingsoldier.example.bootweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.codingsoldier.example.bootweb.common.LocalDateTimeTestAnnoDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HttpTestAnnoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer age;
    private String name;

    /**
     *  @DateTimeFormat 时间格式化，主要是前后到后台的时间格式的转换
     *  @JsonFormat序列化器，后台到前台的时间格式的转换
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private LocalDate localDate;

    @JsonDeserialize(using = LocalDateTimeTestAnnoDeserializer.class)
    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;

}
