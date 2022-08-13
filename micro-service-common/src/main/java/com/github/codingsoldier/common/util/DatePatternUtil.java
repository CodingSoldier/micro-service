package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 时间字符串转时间
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class DatePatternUtil {

    /**
     * 格式化
     */
    private static final Map<Pattern, String> PATTERN_MAP = new HashMap<>();
    private static final List<Pattern> PATTERN_LIST = new ArrayList<>(32);
    private static final Pattern PATTERN3 = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})");
    private static final Pattern PATTERN4 = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2})");
    private static final Pattern PATTERN5 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}");
    private static final Pattern PATTERN6 = Pattern
            .compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d+");
    private static final Pattern PATTERN7 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}");
    private static final Pattern PATTERN71 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}");

    static {
        PATTERN_MAP.put(PATTERN3, "yyyy-MM-dd");
        PATTERN_MAP.put(PATTERN4, "yyyy-MM-dd HH:mm");
        PATTERN_MAP.put(PATTERN5, "yyyy-MM-dd HH:mm:ss");
        PATTERN_MAP.put(PATTERN6, "yyyy-MM-dd HH:mm:ss.SSS");
        PATTERN_MAP.put(PATTERN7, "yyyy/MM/dd");
        PATTERN_MAP.put(PATTERN71, "yyyy/MM/dd HH:mm:ss");

        // 添加pattern
        PATTERN_LIST.add(PATTERN3);
        PATTERN_LIST.add(PATTERN4);
        PATTERN_LIST.add(PATTERN5);
        PATTERN_LIST.add(PATTERN6);
        PATTERN_LIST.add(PATTERN7);
        PATTERN_LIST.add(PATTERN71);
    }

    private DatePatternUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * 获取需要反序列化为正确格式的日期
     *
     * @param strDateValue 字符串类型的日期值
     * @return Date
     */
    public static LocalDateTime strToLocalDateTime(String strDateValue) {
        if (StringUtils.isBlank(strDateValue)) {
            return null;
        }
        // 解决字符串被自动转码导致的问题，在此将转码后的字符串还原。
        char ch = '%';
        if (strDateValue.indexOf(ch) >= 0) {
            try {
                strDateValue = URLDecoder.decode(strDateValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("时间转换编码异常", e);
            }
        }

        LocalDateTime result = null;
        String format = getMatchFormat(strDateValue);
        if (format == null) {
            // 如果以上8种时间格式都无法匹配，校验是否是时间戳格式，如果是就直接转换为Date，否则直接抛出异常
            String regex = "[-]?\\d+";
            Matcher matcher = Pattern.compile(regex).matcher(strDateValue);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                result = DateUtil.toLocalDateTime(CommonUtil.parseLong(strDateValue));
            }
        } else if (PATTERN3.matcher(strDateValue).matches()) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[['T'HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .toFormatter();
            result = LocalDateTime.parse(strDateValue, formatter);
        } else if (PATTERN7.matcher(strDateValue).matches()) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy/MM/dd[['T'HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .toFormatter();
            result = LocalDateTime.parse(strDateValue, formatter);
        } else {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            result = LocalDateTime.parse(strDateValue, dtf);
        }

        return result;
    }

    /**
     * 根据值获取合适的格式
     *
     * @param value 数据
     * @return 格式
     */
    private static String getMatchFormat(final String value) {
        Pattern pattern;
        for (Iterator<Pattern> iterator = PATTERN_LIST.iterator(); iterator.hasNext(); ) {
            pattern = iterator.next();
            Matcher matcher = pattern.matcher(value);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                return PATTERN_MAP.get(pattern);
            }
        }
        return null;
    }

}
