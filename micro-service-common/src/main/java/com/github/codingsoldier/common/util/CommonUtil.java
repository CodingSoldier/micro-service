package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 公共方法
 *
 * @author chenpq05
 * @since 2022-02-09
 */
@Slf4j
public class CommonUtil {

    private CommonUtil() {
        // sonar检测
        throw new IllegalStateException("不允许实例化");
    }

    /**
     * Result.code转Response.status
     */
    public static int getResponseStatus(int resultCode) {
        String codeStr = String.valueOf(resultCode);
        boolean startWith300 = codeStr.startsWith("3") && codeStr.length() >= 3 && resultCode >= 300;
        boolean startWith400 = codeStr.startsWith("4") && codeStr.length() >= 3 && resultCode >= 400;
        boolean startWith500 = codeStr.startsWith("5") && codeStr.length() >= 3 && resultCode >= 500;
        if (startWith300 || startWith400 || startWith500) {
            String prefix3 = codeStr.substring(0, 3);
            return Integer.parseInt(prefix3);
        }
        return 400;
    }

    /**
     * 返回32位UUID
     *
     * @return String
     */
    public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 字符串不相等
     * @param str1 str1
     * @param str2 str2
     * @return boolean
     */
    public static boolean strNotEqual(String str1, String str2) {
        return !StringUtils.equals(str1, str2);
    }

    /**
     * object转string
     * 若 object == null 返回 ""
     *
     * @param obj Object
     * @return String
     */
    public static String objToStr(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof String) {
            return obj.toString();
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * Object转long
     * @param object object
     * @param defaultValue 失败时返回默认值
     * @return long
     */
    public static long parseLong(Object object, long defaultValue) {
        String str = objToStr(object);
        long num = defaultValue;
        if (StringUtils.isNotBlank(str)) {
            try {
                num = Long.parseLong(str.trim());
            } catch (Exception ex) {
                log.warn("字符串转Long异常", ex);
            }
        }
        return num;
    }

    /**
     * Object转Long
     * @param object object
     * @return 可能为null
     */
    public static Long parseLong(Object object) {
        String str = objToStr(object);
        Long num = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                num = Long.parseLong(str.trim());
            } catch (Exception ex) {
                log.warn("字符串转Long异常", ex);
            }
        }
        return num;
    }

    /**
     * Object转int
     *
     * @param object Object
     * @param defaultValue 失败时返回默认值
     * @return int
     */
    public static int parseInt(Object object, int defaultValue) {
        String str = objToStr(object);
        int num = defaultValue;
        if (StringUtils.isNotBlank(str)) {
            try {
                num = Integer.parseInt(str.trim());
            } catch (Exception ex) {
                log.warn("字符串转Integer异常", ex);
            }
        }
        return num;
    }

    /**
     * 字符串转Integer
     *
     * @param object Object
     * @return 返回值可能为null
     */
    public static Integer parseInteger(Object object) {
        String str = objToStr(object);
        Integer num = null;
        if (StringUtils.isNotBlank(str)) {
            try {
                num = Integer.parseInt(str.trim());
            } catch (Exception ex) {
                log.warn("字符串转Integer异常", ex);
            }
        }
        return num;
    }

}



