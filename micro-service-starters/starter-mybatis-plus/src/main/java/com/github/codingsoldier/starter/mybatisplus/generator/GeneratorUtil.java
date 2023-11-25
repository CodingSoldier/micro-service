package com.github.codingsoldier.starter.mybatisplus.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class GeneratorUtil {

    /**
     * 没有下划线且有firstIsUpperCase
     * @param line 字符串
     * @param firstIsUpperCase 第一个字母是否是大写
     * @return String
     */
    private static String notUnderlineCase(String line, boolean... firstIsUpperCase) {
        StringBuilder sb = new StringBuilder();
        String str;
        if (!firstIsUpperCase[0]) {
            sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
        } else {
            sb.append(line.substring(0, 1).toUpperCase()).append(line.substring(1));
        }
        str = sb.toString();
        return str;
    }

    private static String notUnderlineNotCase(String line, String underLine) {
        StringBuilder sb = new StringBuilder();
        String str;
        String[] strArr = line.split(underLine);
        for (String s : strArr) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
        }
        str = sb.toString();
        str = str.substring(0, 1).toLowerCase() + str.substring(1);
        return str;
    }

    private static String underlineCase(String line, String underLine, boolean... firstIsUpperCase) {
        StringBuilder sb = new StringBuilder();
        String str;
        String[] strArr = line.split(underLine);
        for (String s : strArr) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
        }
        if (!firstIsUpperCase[0]) {
            str = sb.toString();
            str = str.substring(0, 1).toLowerCase() + str.substring(1);
        } else {
            str = sb.toString();
        }
        return str;
    }

    /**
     * 下划线转换为驼峰
     *
     * @param line             下划线字符串
     * @param firstIsUpperCase 首字母是否转换为大写
     * @return String
     */
    private static String underline2Camel(String line, boolean... firstIsUpperCase) {
        String str = "";
        String underLine = "_";
        if (StringUtils.isBlank(line)) {
            return str;
        }

        // 不包含下划线，且第二个参数是空的
        if (!line.contains(underLine) && firstIsUpperCase.length == 0) {
            str = line.substring(0, 1).toLowerCase() + line.substring(1);
        } else if (!line.contains(underLine) && firstIsUpperCase.length != 0) {
            str = notUnderlineCase(line, firstIsUpperCase);
        } else if (line.contains(underLine) && firstIsUpperCase.length == 0) {
            str = notUnderlineNotCase(line, underLine);
        } else if (line.contains(underLine) && firstIsUpperCase.length != 0) {
            str = underlineCase(line, underLine, firstIsUpperCase);
        }
        return str;
    }

    public static String delPrefix(String content, String prefix) {
        if (StringUtils.isNotEmpty(prefix) && content.startsWith(prefix)) {
            content = StringUtils.replaceOnce(content, prefix, "");
        }
        return content;
    }

    public static String tableJavaName(String tableName) {
        String notPrefixTableName = delPrefix(tableName, MybatisPlusCodeGenerator.TABLE_PREFIX);
        return underline2Camel(notPrefixTableName, true);
    }
}