package com.github.codingsoldier.common.util.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        LocalDate localDate = LocalDate.of(2022, 8, 13);
        assertEquals(new Date(1660320000000L), DateUtil.toDate(t.toLocalDate()));
    }

    @Test
    void testToString() {
        LocalDateTime t = LocalDateTime.of(2022, 8, 13, 07, 10, 21);
        assertEquals("2022-08-13 07:10:21", DateUtil.toPatternString(t, DateUtil.YMDHMS_FORMAT_STR));
        assertEquals("2022-08-13", DateUtil.toYyyyMMdd(t));
        assertEquals("2022-08-13", DateUtil.toYyyyMMdd(t.toLocalDate()));
        assertEquals("2022-08-13 07:10:21", DateUtil.toYyyyMMddHHmmss(t));
        Date date = DateUtil.toDate(t);
        assertEquals("2022-08-13 07:10:21", DateUtil.toYyyyMmDdHmMmSs(date));
    }


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