package com.github.codingsoldier.common.util.objectmapper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.codingsoldier.common.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Date、LocalDateTime、OffsetDateTime、LocalDate 序列化为 时间戳
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class TimeToTimestampSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Objects.isNull(value)) {
            return;
        }
        if (value instanceof Date) {
            Date timeObj = (Date) value;
            /**
             * @JsonFormat 源码位置
             * com.fasterxml.jackson.databind.ser.std.DateSerializer.serialize
             * com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase#_serializeAsString()
             * 太复杂，不实现
             */
            gen.writeNumber(timeObj.getTime());
        } else if (value instanceof LocalDateTime) {
            LocalDateTime timeObj = (LocalDateTime) value;
            Long timestamp = DateUtil.toTimestamp(timeObj);
            gen.writeNumber(timestamp);
        } else if (value instanceof OffsetDateTime) {
            OffsetDateTime timeObj = (OffsetDateTime) value;
            Long timestamp = DateUtil.toTimestamp(timeObj);
            gen.writeNumber(timestamp);
        } else if (value instanceof LocalDate) {
            LocalDate timeObj = (LocalDate) value;
            long timestamp = DateUtil.toTimestamp(timeObj);
            gen.writeNumber(timestamp);
        }
    }

}
