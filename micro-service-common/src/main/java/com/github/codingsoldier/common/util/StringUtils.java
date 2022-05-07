package com.github.codingsoldier.common.util;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 结尾符号
     */
    public static final String END_CHAR = ".*[,.，。、]$";

    /**
     * 字符串结尾是否符合正则表达式
     *
     * @param str   字符串
     * @param regex 正则 例如： ".*[,.，。、]$"
     * @return
     */
    public static boolean isEndWith(String str, String regex) {
        if (isAnyBlank(str, regex)) {
            return false;
        }
        return Pattern.matches(regex, str);
    }

}
