package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.codingsoldier.common.util.date.DatePatternUtil;
import com.github.codingsoldier.common.util.date.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * OffsetDateTime反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String timestamp = p.getValueAsString();
        LocalDateTime localDateTime = DatePatternUtil.strToLocalDateTime(timestamp);
        return DateUtil.toOffsetDateTime(localDateTime);
    }
}