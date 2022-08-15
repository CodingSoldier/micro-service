package com.github.codingsoldier.common.util.objectmapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.codingsoldier.common.util.DateUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Date、LocalDateTime、OffsetDateTime 序列化为 yyyy-MM-dd HH:mm:ss
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class TimeToyyyyMMddHHmmssMiddleSerializer extends JsonSerializer<Object> {

    private String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            return;
        }
        if (value instanceof Date) {
            Date timeObj = (Date) value;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String timeStr = sdf.format(timeObj);
            gen.writeString(timeStr);
        }else if (value instanceof LocalDateTime) {
            LocalDateTime timeObj = (LocalDateTime) value;
            String timeStr = timeObj.format(DateTimeFormatter.ofPattern(pattern));
            gen.writeString(timeStr);
        } else if (value instanceof OffsetDateTime) {
            OffsetDateTime timeObj = (OffsetDateTime) value;
            String timeStr = timeObj.format(DateTimeFormatter.ofPattern(pattern));
            gen.writeString(timeStr);
        }
    }


}
