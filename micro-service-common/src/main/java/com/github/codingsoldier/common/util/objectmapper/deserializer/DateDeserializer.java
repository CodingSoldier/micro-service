package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.github.codingsoldier.common.util.date.DatePatternUtil;
import com.github.codingsoldier.common.util.date.DateUtil;
import java.time.LocalDateTime;
import java.util.Date;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * Date反序列化
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class DateDeserializer extends ValueDeserializer<Date> {

  @Override
  public Date deserialize(JsonParser p, DeserializationContext ctxt)
      throws JacksonException {
    String timestamp = p.getValueAsString();
    LocalDateTime localDateTime = DatePatternUtil.strToLocalDateTime(timestamp);
    return DateUtil.toDate(localDateTime);
  }

}
