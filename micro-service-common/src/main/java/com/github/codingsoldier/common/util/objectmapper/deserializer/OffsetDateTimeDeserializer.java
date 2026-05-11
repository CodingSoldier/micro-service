package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.github.codingsoldier.common.util.date.DatePatternUtil;
import com.github.codingsoldier.common.util.date.DateUtil;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * OffsetDateTime反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class OffsetDateTimeDeserializer extends ValueDeserializer<OffsetDateTime> {

  @Override
  public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
      throws JacksonException {
    String timestamp = p.getValueAsString();
    LocalDateTime localDateTime = DatePatternUtil.strToLocalDateTime(timestamp);
    return DateUtil.toOffsetDateTime(localDateTime);
  }
}
