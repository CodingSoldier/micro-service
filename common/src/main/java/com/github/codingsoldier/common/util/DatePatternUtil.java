package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DatePatternUtil {

    /**
     * 构造方法
     */
    private DatePatternUtil() {

    }

    /** 格式化 */
    private static final Map<Pattern, String> patternMap = new HashMap<Pattern, String>();
    private static final List<Pattern> patternList = new ArrayList<Pattern>(5);

    private static final Pattern PATTERN1 = Pattern.compile("\\d{4}");
    private static final Pattern PATTERN2 = Pattern.compile("\\d{4}-\\d{1,2}");
    private static final Pattern PATTERN3 = Pattern.compile("(\\d{4}\\-\\d{1,2}\\-\\d{1,2})");
    private static final Pattern PATTERN4 = Pattern.compile("(\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2})");
    private static final Pattern PATTERN5 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}");
    private static final Pattern PATTERN6 = Pattern
            .compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d+");
    private static final Pattern PATTERN7 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}");
    private static final Pattern PATTERN71 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}");
    private static final Pattern PATTERN8 = Pattern
            .compile("\\w{3}\\s\\w{3}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\sGMT\\+0800");
    private static final Pattern PATTERN9 = Pattern.compile("\\d{4}\\d{1,2}");
    private static final Pattern PATTERN10 = Pattern.compile("(\\d{4}\\d{1,2}\\d{1,2})");


    static {
        // patternMap.put(PATTERN1, "yyyy");
        // patternMap.put(PATTERN2, "yyyy-MM");
        patternMap.put(PATTERN3, "yyyy-MM-dd");
        patternMap.put(PATTERN4, "yyyy-MM-dd HH:mm");
        patternMap.put(PATTERN5, "yyyy-MM-dd HH:mm:ss");
        patternMap.put(PATTERN6, "yyyy-MM-dd HH:mm:ss.SSS");
        patternMap.put(PATTERN7, "yyyy/MM/dd");
        patternMap.put(PATTERN71, "yyyy/MM/dd HH:mm:ss");
        // patternMap.put(PATTERN8, "EEE MMM dd yyyy HH:mm:ss 'GMT+0800'");
        // patternMap.put(PATTERN9, "yyyyMM");
        // patternMap.put(PATTERN10, "yyyyMMdd");
        // patternMap.put(PATTERN10, "yyyyMMdd");

        // 添加pattern
        // patternList.add(PATTERN1);
        // patternList.add(PATTERN2);
        patternList.add(PATTERN3);
        patternList.add(PATTERN4);
        patternList.add(PATTERN5);
        patternList.add(PATTERN6);
        patternList.add(PATTERN7);
        patternList.add(PATTERN71);
        // patternList.add(PATTERN8);
        // patternList.add(PATTERN9);
        // patternList.add(PATTERN10);
    }

    /**
     * 获取需要反序列化为正确格式的日期
     *
     * @param strDateValue 字符串类型的日期值
     * @return Date
     */
    public static LocalDateTime getPatternDate(String strDateValue) {
        if (StringUtils.isBlank(strDateValue)) {
            return null;
        }
        // 解决字符串被自动转码导致的问题，在此将转码后的字符串还原。
        if (strDateValue.indexOf('%') >= 0) {
            try {
                strDateValue = URLDecoder.decode(strDateValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("时间转换编码异常", strDateValue);
            }
        }

        LocalDateTime result = null;
        String format = getMatchFormat(strDateValue);
        if (format == null) {
            // 如果以上8种时间格式都无法匹配，校验是否是时间戳格式，如果是就直接转换为Date，否则直接抛出异常
            Matcher matcher = Pattern.compile("[-]?\\d+").matcher(strDateValue);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                result = DateUtil.toLocalDateTime(CommonUtil.parseLong(strDateValue));
            }
        } else if (PATTERN3.matcher(strDateValue).matches()){
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[['T'HH][:mm][:ss]]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                    .toFormatter();
            result = LocalDateTime.parse(strDateValue, formatter);
        } else if (PATTERN7.matcher(strDateValue).matches()){
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
            result = LocalDateTime.parse(strDateValue,dtf);
        }

        return result;
    }

    /**
     * 根据值获取合适的格式
     *
     *
     * @param value 数据
     * @return 格式
     */
    private static String getMatchFormat(final String value) {
        Pattern pattern = null;
        for (Iterator<Pattern> iterator = patternList.iterator(); iterator.hasNext();) {
            pattern = iterator.next();
            Matcher matcher = pattern.matcher(value);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                return patternMap.get(pattern);
            }
        }
        return null;
    }

}
