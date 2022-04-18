package com.github.codingsoldier.bootweb.dto;

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
public class WebTestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer age;
    private String name;
    private Date date;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;

}
