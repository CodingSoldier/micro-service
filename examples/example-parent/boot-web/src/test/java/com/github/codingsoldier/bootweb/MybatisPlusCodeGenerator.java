package com.github.codingsoldier.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.CodeGenerator;

public class MybatisPlusCodeGenerator {

    /**
     * 注意，旧文件不会被覆盖
     */
    public static void main(String[] args) {
        // 数据库url
        CodeGenerator.dbUrl = "jdbc:mysql://localhost:3306/cpq?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        // 数据库账号
        CodeGenerator.dbUsername = "root";
        // 数据库密码
        CodeGenerator.dbPassword = "cpq..123";

        // 项目 main 目录的绝对路径
        CodeGenerator.srcMainAbsolutePath = "E:\\github\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        // 项目包名
        CodeGenerator.parent = "com.github.codingsoldier.bootweb.temp";

        // 作者
        CodeGenerator.author = "cpq";
        // 表名
        CodeGenerator.tableName = "demo";

        // 生成代码
        CodeGenerator.generate();

    }
}