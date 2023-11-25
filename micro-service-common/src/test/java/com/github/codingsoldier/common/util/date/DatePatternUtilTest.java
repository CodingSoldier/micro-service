package com.github.codingsoldier.common.util.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatePatternUtilTest {

    @Test
    void getPatternDate() {

        LocalDateTime localDateTime = LocalDateTime.of(2020, 1, 25, 0, 0, 0);
        assertEquals(localDateTime, DatePatternUtil.strToLocalDateTime("2020-01-25"));

        localDateTime = LocalDateTime.of(2020, 1, 25, 10, 12, 0);
        assertEquals(localDateTime, DatePatternUtil.strToLocalDateTime("2020-01-25 10:12"));

        localDateTime = LocalDateTime.of(2020, 1, 25, 10, 12, 26);
        assertEquals(localDateTime, DatePatternUtil.strToLocalDateTime("2020-01-25 10:12:26"));

        localDateTime = LocalDateTime.of(2020, 1, 25, 10, 12, 26, 100);
        assertEquals(localDateTime.getSecond(), DatePatternUtil.strToLocalDateTime("2020-01-25 10:12:26.100").getSecond());

        localDateTime = LocalDateTime.of(2020, 1, 25, 0, 0, 0);
        assertEquals(localDateTime, DatePatternUtil.strToLocalDateTime("2020/01/25"));

        localDateTime = LocalDateTime.of(2020, 1, 25, 10, 12, 26);
        assertEquals(localDateTime, DatePatternUtil.strToLocalDateTime("2020/01/25 10:12:26"));

    }
}