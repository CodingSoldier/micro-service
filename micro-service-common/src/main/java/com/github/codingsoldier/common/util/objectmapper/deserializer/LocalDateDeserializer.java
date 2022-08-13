package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.codingsoldier.common.util.DatePatternUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * LocalDate反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String timestamp = p.getValueAsString();
        LocalDateTime localDateTime = DatePatternUtil.strToLocalDateTime(timestamp);
        return localDateTime != null ? localDateTime.toLocalDate() : null;
    }
}