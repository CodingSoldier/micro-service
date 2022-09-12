package com.github.codingsoldier.example.cloudwebapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DemoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate localDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime localDateTime;

}
