package com.github.codingsoldier.common.util;

import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @author chenpq05
 * @since 2022/2/23 14:14
 */
public class DateUtil {

    public static final ZoneId ZONE_ID_DEFAULT = ZoneId.systemDefault();
    public static final ZoneOffset ZONE_OFFSET_8 = ZoneOffset.of("+8");
    private DateUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * localDate转时间戳
     *
     * @param localDate
     * @return
     */
    public static Long toTimestamp(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        return localDate.atStartOfDay(ZONE_OFFSET_8).toInstant().toEpochMilli();
    }

    /**
     * localdatetime转时间戳
     *
     * @param localDateTime
     * @return
     */
    public static Long toTimestamp(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return null;
        }
        return localDateTime.toInstant(ZONE_OFFSET_8).toEpochMilli();
    }

    /**
     * offsetDateTime转时间戳
     *
     * @param offsetDateTime
     * @return
     */
    public static Long toTimestamp(OffsetDateTime offsetDateTime) {
        if (Objects.isNull(offsetDateTime)) {
            return null;
        }
        return offsetDateTime.toInstant().toEpochMilli();
    }

    /**
     * 时间戳转localDatetime
     *
     * @param timestamp
     * @return
     */
    public static LocalDate toLocalDate(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toLocalDate();
    }

    /**
     * 时间戳转localDatetime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toLocalDateTime();
    }

    /**
     * 时间戳转OffsetDateTime
     *
     * @param timestamp
     * @return
     */
    public static OffsetDateTime toOffsetDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toOffsetDateTime();
    }

    /**
     * 字符串转LocalDatetime
     *
     * @param timeString 例如 2018-06-01 23:59:59
     * @param df         例如 DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String timeString, DateTimeFormatter df) {
        if (StringUtils.isBlank(timeString) || Objects.isNull(df)) {
            return null;
        }
        return LocalDateTime.parse(timeString, df);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZONE_ID_DEFAULT);
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * Date转LocalTime
     *
     * @param date
     * @return LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * LocalDateTime 转 OffsetDateTime
     *
     * @param localDateTime
     * @return
     */
    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZONE_OFFSET_8);
    }

    /**
     * offsetDateTime 转 date
     *
     * @param offsetDateTime
     * @return Date
     */
    public static Date toDate(OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) {
            return null;
        }
        return Date.from(offsetDateTime.atZoneSameInstant(ZONE_ID_DEFAULT).toInstant());
    }

    /**
     * localDateTime 转 date
     *
     * @param localDateTime
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ZONE_OFFSET_8));
    }


}
