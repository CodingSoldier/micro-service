package com.github.codingsoldier.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.CodeGenerator;

public class MyGen {

    /**
     * 修改 OUTPUT_DIR、PARENT、AUTHOR、TABLE_NAME 后运行main方法
     * 注意，旧文件不会被覆盖
     * @param args
     */
    public static void main(String[] args) {

        CodeGenerator.dbUrl = "jdbc:mysql://127.0.0.1:3306/cpq";
        CodeGenerator.dbUsername = "root";
        CodeGenerator.dbPassword = "cpq..123";

        CodeGenerator.srcMainAbsolutePath = "E:\\github\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        CodeGenerator.parent = "com.github.codingsoldier.bootweb.temp222";

        CodeGenerator.author = "cpq";
        CodeGenerator.tableName = "demo";

        CodeGenerator.generate();

    }
}