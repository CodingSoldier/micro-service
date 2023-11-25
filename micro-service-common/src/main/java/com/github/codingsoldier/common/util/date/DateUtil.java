package com.github.codingsoldier.common.util.date;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
/**
 * 时间工具类
 *
 * @author chenpq05
 * @since 2022/2/23 14:14
 */
public class DateUtil {

    public static final ZoneId ZONE_ID_DEFAULT = ZoneId.systemDefault();
    public static final ZoneOffset ZONE_OFFSET_8 = ZoneOffset.of("+8");
    public static final String YMD_FORMAT_STR = "yyyy-MM-dd";
    public static final String YMDHMS_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * localDate转时间戳
     *
     * @param localDate localDate
     * @return Long
     */
    public static Long toTimestamp(LocalDate localDate) {
        if (Objects.isNull(localDate)) {
            return null;
        }
        return localDate.atStartOfDay(ZONE_OFFSET_8).toInstant().toEpochMilli();
    }

    /**
     * localDateTime转时间戳
     *
     * @param localDateTime localDateTime
     * @return Long
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
     * @param offsetDateTime offsetDateTime
     * @return Long
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
     * @param timestamp timestamp
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toLocalDate();
    }

    /**
     * Date转LocalDate
     *
     * @param date date
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 时间戳转localDatetime
     *
     * @param timestamp timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toLocalDateTime();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date date
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZONE_ID_DEFAULT);
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
     * 时间戳转OffsetDateTime
     *
     * @param timestamp timestamp
     * @return OffsetDateTime
     */
    public static OffsetDateTime toOffsetDateTime(Long timestamp) {
        if (Objects.isNull(timestamp)) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID_DEFAULT).toOffsetDateTime();
    }

    /**
     * LocalDateTime 转 OffsetDateTime
     *
     * @param localDateTime localDateTime
     * @return OffsetDateTime
     */
    public static OffsetDateTime toOffsetDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZONE_OFFSET_8);
    }

    /**
     * Date转LocalTime
     *
     * @param date date
     * @return LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalTime();
    }


    /**
     * offsetDateTime 转 date
     *
     * @param offsetDateTime offsetDateTime
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
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ZONE_OFFSET_8));
    }

    /**
     * LocalDate 转 date
     *
     * @param localDate localDate
     * @return Date
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 生成日期时间字符串
     *
     * @param dateTime 日期时间
     * @param pattern  日期时间样式
     * @return string 日期时间字符串
     */
    public static String toPatternString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(fmt);
    }

    /**
     * localDateTime 转 yyyy-MM-dd HH:mm:ss
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toYyyyMMddHHmmss(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YMDHMS_FORMAT_STR);
        return localDateTime.format(fmt);
    }

    /**
     * localDateTime 转 yyyy-MM-dd
     * @param localDateTime localDateTime
     * @return yyyy-MM-dd
     */
    public static String toYyyyMMdd(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YMD_FORMAT_STR);
        return localDateTime.format(fmt);
    }

    /**
     * dateTime 转 yyyy-MM-dd
     * @param localDate localDate
     * @return yyyy-MM-dd
     */
    public static String toYyyyMMdd(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YMD_FORMAT_STR);
        return localDate.format(fmt);
    }

    /**
     * date 转 yyyy-MM-dd HH:mm:ss
     *
     * @param date date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String toYyyyMmDdHmMmSs(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(YMDHMS_FORMAT_STR);
        return f.format(date);
    }

}
