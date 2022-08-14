package com.github.codingsoldier.example.bootweb.vo;

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
     *  @JsonFormat 参数、返回值都可以用
     *  不支持 @JsonFormat
     *  统一返回时间戳
     */
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date date;

    private LocalDate localDate;

    @JsonSerialize(using = DateAllTestAnnoSerializer.class)
    private LocalDateTime localDateTime;

    private OffsetDateTime offsetDateTime;
}
