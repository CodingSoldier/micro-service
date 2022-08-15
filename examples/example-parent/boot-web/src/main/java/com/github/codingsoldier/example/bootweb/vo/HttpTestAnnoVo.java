package com.github.codingsoldier.example.bootweb.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToyyyyMMddHHmmssMiddleSerializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToyyyyMMddHHmmssSlashSerializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToyyyyMMddMiddleSerializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToyyyyMMddSlashSerializer;
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
     *  不支持 @JsonFormat、@JsonFormat
     */
    @JsonSerialize(using = TimeToyyyyMMddHHmmssMiddleSerializer.class)
    private Date dateMiddle;

    // @JsonSerialize(using = TimeToyyyyMMddHHmmssMiddleSerializer.class)
    private LocalDate localDateMiddle;

    @JsonSerialize(using = TimeToyyyyMMddHHmmssMiddleSerializer.class)
    private LocalDateTime localDateTimeMiddle;

    @JsonSerialize(using = TimeToyyyyMMddHHmmssMiddleSerializer.class)
    private OffsetDateTime offsetDateTimeMiddle;





    @JsonSerialize(using = TimeToyyyyMMddHHmmssSlashSerializer.class)
    private Date dateSlash;

    // @JsonSerialize(using = TimeToyyyyMMddHHmmssMiddleSerializer.class)
    private LocalDate localDateSlash;

    @JsonSerialize(using = TimeToyyyyMMddHHmmssSlashSerializer.class)
    private LocalDateTime localDateTimeSlash;

    @JsonSerialize(using = TimeToyyyyMMddHHmmssSlashSerializer.class)
    private OffsetDateTime offsetDateTimeSlash;




    @JsonSerialize(using = TimeToyyyyMMddMiddleSerializer.class)
    private Date dateMiddleDay;

    @JsonSerialize(using = TimeToyyyyMMddMiddleSerializer.class)
    private LocalDate localDateMiddleDay;

    @JsonSerialize(using = TimeToyyyyMMddMiddleSerializer.class)
    private LocalDateTime localDateTimeMiddleDay;

    @JsonSerialize(using = TimeToyyyyMMddMiddleSerializer.class)
    private OffsetDateTime offsetDateTimeMiddleDay;




    @JsonSerialize(using = TimeToyyyyMMddSlashSerializer.class)
    private Date dateSlashDay;

    @JsonSerialize(using = TimeToyyyyMMddSlashSerializer.class)
    private LocalDate localDateSlashDay;

    @JsonSerialize(using = TimeToyyyyMMddSlashSerializer.class)
    private LocalDateTime localDateTimeSlashDay;

    @JsonSerialize(using = TimeToyyyyMMddSlashSerializer.class)
    private OffsetDateTime offsetDateTimeSlashDay;
}
