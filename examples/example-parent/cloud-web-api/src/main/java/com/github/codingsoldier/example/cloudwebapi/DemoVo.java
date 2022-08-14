package com.github.codingsoldier.example.cloudwebapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.codingsoldier.common.util.objectmapper.deserializer.DateDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.OffsetDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToTimestampSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class DemoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    @JsonDeserialize(using= DateDeserializer.class)
    @JsonSerialize(using = TimeToTimestampSerializer.class)
    private Date date;

    @JsonDeserialize(using= LocalDateDeserializer.class)
    @JsonSerialize(using = TimeToTimestampSerializer.class)
    private LocalDate localDate;

    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonSerialize(using = TimeToTimestampSerializer.class)
    private LocalDateTime localDateTime;

    @JsonDeserialize(using= OffsetDateTimeDeserializer.class)
    @JsonSerialize(using = TimeToTimestampSerializer.class)
    private OffsetDateTime offsetDateTime;

}
