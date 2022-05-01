package com.github.codingsoldier.bootweb;


import java.util.Arrays;

public class MyGen {

    /**
     * 修改 OUTPUT_DIR、PARENT、AUTHOR、TABLE_NAMES 后运行main方法
     * 注意，旧文件不会被覆盖
     * @param args
     */
    public static void main(String[] args) {

        MybatisPlusGenerator.dbUrl = "jdbc:mysql://127.0.0.1:3306/cpq";
        MybatisPlusGenerator.dbUsername = "root";
        MybatisPlusGenerator.dbPassword = "cpq..123";

        MybatisPlusGenerator.srcMainAbsolutePath = "E:\\github\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        MybatisPlusGenerator.parent = "com.github.codingsoldier.bootweb.temp";

        MybatisPlusGenerator.author = "cpq";
        MybatisPlusGenerator.tableNames = Arrays.asList("demo");

        MybatisPlusGenerator.generate();

    }
}