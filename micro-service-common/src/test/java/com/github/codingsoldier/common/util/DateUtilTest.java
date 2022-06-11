package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class DateUtilTest {

    @Test
    void testDateTime() {

        assertEquals("2022-03-14", DateUtil.toLocalDate(1647187261000L).toString());
        assertEquals("2022-03-14T00:01:01", DateUtil.toLocalDateTime(1647187261000L).toString());
        assertEquals("2022-03-14T00:01:01+08:00", DateUtil.toOffsetDateTime(1647187261000L).toString());

        assertEquals("2022-03-14", DateUtil.toLocalDate(1647187261000L).toString());
        assertEquals("2022-03-14T23:59:01", DateUtil.toLocalDateTime(1647273541000L).toString());
        assertEquals("2022-03-14T23:59:01+08:00", DateUtil.toOffsetDateTime(1647273541000L).toString());

        long currentTimeMillis = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        assertTrue(currentTimeMillis - 10L < DateUtil.toTimestamp(localDateTime));
        assertTrue(currentTimeMillis + 10L > DateUtil.toTimestamp(localDateTime));
        assertTrue(currentTimeMillis - 10L < DateUtil.toTimestamp(offsetDateTime));
        assertTrue(currentTimeMillis + 10L > DateUtil.toTimestamp(offsetDateTime));

    }

}