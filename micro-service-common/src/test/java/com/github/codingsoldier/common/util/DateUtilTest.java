package com.github.codingsoldier.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {

    @Test
    void toTimestamp() {
        LocalDate t = LocalDate.of(2022, 8, 13);
        assertEquals(1660320000000L, DateUtil.toTimestamp(t));
    }

    @Test
    void testToTimestamp() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        assertEquals(1660345821000L, DateUtil.toTimestamp(t));
    }

    @Test
    void testToTimestamp1() {
        OffsetDateTime t = OffsetDateTime.of(2022, 8, 13, 07, 10, 21, 10, DateUtil.ZONE_OFFSET_8);
        assertEquals(1660345821000L, DateUtil.toTimestamp(t));
    }

    @Test
    void toLocalDate() {
        LocalDate t = LocalDate.of(2022, 8, 13);
        assertEquals(t, DateUtil.toLocalDate(1660320000000L));
    }

    @Test
    void toLocalDateTime() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        assertEquals(t, DateUtil.toLocalDateTime(1660345821000L));
    }

    @Test
    void toOffsetDateTime() {
        OffsetDateTime t = OffsetDateTime.of(2022, 8, 13, 07, 10, 21, 0, DateUtil.ZONE_OFFSET_8);
        assertEquals(t, DateUtil.toOffsetDateTime(1660345821000L));
    }

    @Test
    void testToLocalDateTime() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        LocalDateTime localDateTime = DateUtil.toLocalDateTime("2022-08-13 07:10:21", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(t, localDateTime);
    }

    @Test
    void testToLocalDateTime1() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        Date date = new Date(1660345821000L);
        assertEquals(t, DateUtil.toLocalDateTime(date));

    }

    @Test
    void testToLocalDate() {
        LocalDate t = LocalDate.of(2022, 8, 13);
        Date date = new Date(1660320000000L);
        assertEquals(t, DateUtil.toLocalDate(date));
    }

    @Test
    void toLocalTime() {
        Date date = new Date(1660345821000L);
        assertEquals(07, DateUtil.toLocalTime(date).getHour());
        assertEquals(10, DateUtil.toLocalTime(date).getMinute());
        assertEquals(21, DateUtil.toLocalTime(date).getSecond());
    }

    @Test
    void testToOffsetDateTime() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        OffsetDateTime o = OffsetDateTime.of(2022, 8, 13, 07, 10, 21, 0, DateUtil.ZONE_OFFSET_8);
        assertEquals(o, DateUtil.toOffsetDateTime(t));
    }

    @Test
    void toDate() {
        OffsetDateTime t = OffsetDateTime.of(2022, 8, 13, 07, 10, 21, 0, DateUtil.ZONE_OFFSET_8);
        assertEquals(new Date(1660345821000L), DateUtil.toDate(t));
    }

    @Test
    void testToDate() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        assertEquals(new Date(1660345821000L), DateUtil.toDate(t));
    }
}