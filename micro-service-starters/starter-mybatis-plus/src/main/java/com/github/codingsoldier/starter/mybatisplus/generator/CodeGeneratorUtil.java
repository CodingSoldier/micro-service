package com.github.codingsoldier.starter.mybatisplus.generator;

import com.github.codingsoldier.common.util.StringUtils;

public class CodeGeneratorUtil {

    /**
     * 下划线转换为驼峰
     *
     * @param line 下划线字符串
     * @param firstIsUpperCase 首字母是否转换为大写
     * @return
     */
    private static String underline2Camel(String line, boolean ... firstIsUpperCase) {
        String str = "";

        if(StringUtils.isBlank(line)){
            return str;
        } else {
            StringBuilder sb = new StringBuilder();
            String [] strArr;
            // 不包含下划线，且第二个参数是空的
            if(!line.contains("_") && firstIsUpperCase.length == 0){
                sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
                str = sb.toString();
            } else if (!line.contains("_") && firstIsUpperCase.length != 0){
                if (!firstIsUpperCase[0]) {
                    sb.append(line.substring(0, 1).toLowerCase()).append(line.substring(1));
                    str = sb.toString();
                } else {
                    sb.append(line.substring(0, 1).toUpperCase()).append(line.substring(1));
                    str = sb.toString();
                }
            } else if (line.contains("_") && firstIsUpperCase.length == 0) {
                strArr = line.split("_");
                for (String s : strArr) {
                    sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                }
                str = sb.toString();
                str = str.substring(0, 1).toLowerCase() + str.substring(1);
            } else if (line.contains("_") && firstIsUpperCase.length != 0) {
                strArr = line.split("_");
                for (String s : strArr) {
                    sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                }
                if (!firstIsUpperCase[0]) {
                    str = sb.toString();
                    str = str.substring(0, 1).toLowerCase() + str.substring(1);
                } else {
                    str = sb.toString();
                }
            }
        }
        return str;
    }

    public static String delPrefix(String content, String prefix) {
        if (StringUtils.isNotEmpty(prefix) && content.startsWith(prefix)){
            content = StringUtils.replaceOnce(content, prefix, "");
        }
        return content;
    }

    public static String tableJavaName(String tableName) {
        String notPrefixTableName = delPrefix(tableName, CodeGenerator.TABLE_PREFIX);
        String tableJavaName = underline2Camel(notPrefixTableName, true);
        return tableJavaName;
    }






    public static void main(String[] args) {
        String tableName = "t_user_copy1";
        if (tableName.startsWith(CodeGenerator.TABLE_PREFIX)){
            tableName = StringUtils.replaceOnce(tableName, CodeGenerator.TABLE_PREFIX, "");
        }
        System.out.println(tableName);
        String user_copy1 = underline2Camel(tableName, true);
        System.out.println(user_copy1);
    }
}