package com.github.codingsoldier.common.util.objectmapper.serializer;

import com.github.codingsoldier.common.util.date.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Date、LocalDateTime、OffsetDateTime、LocalDate 序列化为 时间戳
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class TimeToTimestampSerializer extends ValueSerializer<Object> {

  @Override
  public void serialize(Object value, JsonGenerator gen, SerializationContext serializers)
      throws JacksonException {
    if (Objects.isNull(value)) {
      return;
    }
    if (value instanceof Date) {
      Date timeObj = (Date) value;
      gen.writeNumber(timeObj.getTime());
    } else if (value instanceof LocalDateTime) {
      LocalDateTime timeObj = (LocalDateTime) value;
      Long timestamp = DateUtil.toTimestamp(timeObj);
      if (timestamp != null) {
        gen.writeNumber(timestamp);
      }
    } else if (value instanceof OffsetDateTime) {
      OffsetDateTime timeObj = (OffsetDateTime) value;
      Long timestamp = DateUtil.toTimestamp(timeObj);
      if (timestamp != null) {
        gen.writeNumber(timestamp);
      }
    } else if (value instanceof LocalDate) {
      LocalDate timeObj = (LocalDate) value;
      Long timestamp = DateUtil.toTimestamp(timeObj);
      if (timestamp != null) {
        gen.writeNumber(timestamp);
      }
    }
  }

}
