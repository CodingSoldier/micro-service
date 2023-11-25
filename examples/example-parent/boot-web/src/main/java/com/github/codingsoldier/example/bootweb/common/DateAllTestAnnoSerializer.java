package com.github.codingsoldier.example.bootweb.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.codingsoldier.common.util.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * DateTime序列化
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class DateAllTestAnnoSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        log.info("DateAllTestAnnoSerializer.serialize");
        if (Objects.isNull(value)) {
            return;
        }
        if (value instanceof Date) {
            Date obj = (Date) value;
            gen.writeNumber(obj.getTime());
        } else if (value instanceof LocalDate) {
            LocalDate obj = (LocalDate) value;
            long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime obj = (LocalDateTime) value;
            Long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        } else if (value instanceof OffsetDateTime) {
            OffsetDateTime obj = (OffsetDateTime) value;
            Long timestamp = DateUtil.toTimestamp(obj);
            gen.writeNumber(timestamp);
        }
    }

}
