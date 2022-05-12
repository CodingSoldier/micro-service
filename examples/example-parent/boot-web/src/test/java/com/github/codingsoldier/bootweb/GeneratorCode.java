package com.github.codingsoldier.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.MybatisPlusCodeGenerator;

public class GeneratorCode {

    /**
     * 注意，旧文件不会被覆盖
     */
    public static void main(String[] args) {
        // 数据库url
        MybatisPlusCodeGenerator.dbUrl = "";
        // 数据库账号
        MybatisPlusCodeGenerator.dbUsername = "";
        // 数据库密码
        MybatisPlusCodeGenerator.dbPassword = "";

        // 项目 main 目录的绝对路径
        MybatisPlusCodeGenerator.srcMainAbsolutePath = "D:\\mycode\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        // 项目包名
        MybatisPlusCodeGenerator.parent = "com.github.codingsoldier.bootweb.temp";

        // 作者
        MybatisPlusCodeGenerator.author = "cpq";
        // 表名
        MybatisPlusCodeGenerator.tableName = "device";

        // 生成代码
        MybatisPlusCodeGenerator.generate();

    }
}