package com.github.codingsoldier.example.bootweb.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.codingsoldier.common.util.DatePatternUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LocalDateTime反序列
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class LocalDateTimeTestAnnoDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        log.info("进入LocalDateTimeTestAnnoDeserializer#deserialize");
        String timestamp = p.getValueAsString();
        return DatePatternUtil.strToLocalDateTime(timestamp);
    }
}