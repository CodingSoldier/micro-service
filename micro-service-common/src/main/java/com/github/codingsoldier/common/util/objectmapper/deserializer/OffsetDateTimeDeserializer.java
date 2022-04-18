package com.github.codingsoldier.common.util.objectmapper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.codingsoldier.common.util.DatePatternUtil;
import com.github.codingsoldier.common.util.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {
    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String timestamp = p.getValueAsString();
        LocalDateTime localDateTime = DatePatternUtil.getPatternDate(timestamp);
        return DateUtil.toOffsetDateTime(localDateTime);
    }
}