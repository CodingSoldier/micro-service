package com.github.codingsoldier.starter.mybatisplus.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
public class GeneratorUtil {

    /**
     * 没有下划线且有firstIsUpperCase
     * @param line
     * @param firstIsUpperCase
     * @return
     */
    private static String notUnderlineCase(String line, boolean... firstIsUpperCase) {
        StringBuilder sb = new StringBuilder();
        String str = "";
        if (!firstIsUpperCase[0]) {
            sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
            str = sb.toString();
        } else {
            sb.append(line.substring(0, 1).toUpperCase()).append(line.substring(1));
            str = sb.toString();
        }
        return str;
    }

    private static String notUnderlineNotCase(String line, String underLine) {
        StringBuilder sb = new StringBuilder();
        String str = "";
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
        String str = "";
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
     * @return
     */
    private static String underline2Camel(String line, boolean... firstIsUpperCase) {
        String str = "";
        String underLine = "_";
        if (StringUtils.isBlank(line)) {
            return str;
        }

        // 不包含下划线，且第二个参数是空的
        if (!line.contains(underLine) && firstIsUpperCase.length == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
            str = sb.toString();
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

    @SuppressWarnings("squid:S106")
    public static void main(String[] args) {
        String tableName = "t_user_copy1";
        if (tableName.startsWith(MybatisPlusCodeGenerator.TABLE_PREFIX)) {
            tableName = StringUtils.replaceOnce(tableName, MybatisPlusCodeGenerator.TABLE_PREFIX, "");
        }
        System.out.println(tableName);
        String userCopy1 = underline2Camel(tableName, true);
        System.out.println(userCopy1);
    }
}