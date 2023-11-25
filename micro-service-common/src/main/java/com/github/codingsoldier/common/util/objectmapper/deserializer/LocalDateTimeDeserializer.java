package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.codingsoldier.common.util.date.DatePatternUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LocalDateTime反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String timestamp = p.getValueAsString();
        return DatePatternUtil.strToLocalDateTime(timestamp);
    }
}