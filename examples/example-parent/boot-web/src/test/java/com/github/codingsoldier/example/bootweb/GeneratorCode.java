package com.github.codingsoldier.example.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.MybatisPlusCodeGenerator;

public class GeneratorCode {

    /**
     * 注意，旧文件不会被覆盖
     */
    public static void main(String[] args) {
        // 数据库url
        MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://10.39.174.41:3306/ioc_smartpark?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
        // 数据库账号
        MybatisPlusCodeGenerator.dbUsername = "root";
        // 数据库密码
        MybatisPlusCodeGenerator.dbPassword = "123456";

        // 项目 main 目录的绝对路径
        MybatisPlusCodeGenerator.srcMainAbsolutePath = "D:\\mycode\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        // 项目包名
        MybatisPlusCodeGenerator.parent = "com.github.codingsoldier.temp111";

        // 作者
        MybatisPlusCodeGenerator.author = "cpq";
        // 表名
        MybatisPlusCodeGenerator.tableName = "smartpark_collect_company";

        // 生成代码
        MybatisPlusCodeGenerator.generate();

    }
}