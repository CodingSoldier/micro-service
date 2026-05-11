package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.github.codingsoldier.common.util.date.DatePatternUtil;
import java.time.LocalDateTime;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * LocalDateTime反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class LocalDateTimeDeserializer extends ValueDeserializer<LocalDateTime> {

  @Override
  public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
      throws JacksonException {
    String timestamp = p.getValueAsString();
    return DatePatternUtil.strToLocalDateTime(timestamp);
  }
}
