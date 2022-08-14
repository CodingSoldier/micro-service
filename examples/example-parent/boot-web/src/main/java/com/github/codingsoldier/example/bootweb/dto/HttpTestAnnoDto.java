package com.github.codingsoldier.example.bootweb.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.codingsoldier.example.bootweb.common.LocalDateTimeTestAnnoDeserializer;
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
public class HttpTestAnnoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer age;
    private String name;

    /**
     *  @JsonFormat 参数、返回值都可以用
     *  不支持 @JsonFormat
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    private LocalDate localDate;

    @JsonDeserialize(using = LocalDateTimeTestAnnoDeserializer.class)
    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;

}
