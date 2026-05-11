package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.github.codingsoldier.common.util.date.DatePatternUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * LocalDate反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class LocalDateDeserializer extends ValueDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
      throws JacksonException {
    String timestamp = p.getValueAsString();
    LocalDateTime localDateTime = DatePatternUtil.strToLocalDateTime(timestamp);
    return localDateTime != null ? localDateTime.toLocalDate() : null;
  }
}
