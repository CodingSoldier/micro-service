package com.github.codingsoldier.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
public class StringUtil {

    /**
     * 结尾符号
     */
    public static final String END_CHAR = ".*[,.，。、]$";

    private StringUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * 字符串结尾是否符合正则表达式
     *
     * @param str   字符串
     * @param regex 正则 例如： ".*[,.，。、]$"
     * @return boolean
     */
    public static boolean isEndWith(String str, String regex) {
        if (StringUtils.isAnyBlank(str, regex)) {
            return false;
        }
        return Pattern.matches(regex, str);
    }

}
