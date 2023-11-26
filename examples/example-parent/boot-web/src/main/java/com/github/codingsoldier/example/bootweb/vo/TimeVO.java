package com.github.codingsoldier.example.bootweb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(name = "时间VO", description = "时间VO")
public class TimeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id", example = "7", required = true)
    private Long id;

    @Schema(description = "姓名", example = "姓名01", required = true)
    private String name;

    @Schema(description = "年龄", example = "10")
    private Integer age;

    @Schema(description = "date时间", example = "2021-01-01 01:01:01")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    @Schema(description = "localDate时间", example = "2021-01-01")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate localDate;

    @Schema(description = "localDateTime时间", example = "2021-01-01 01:01:01")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime localDateTime;

}
