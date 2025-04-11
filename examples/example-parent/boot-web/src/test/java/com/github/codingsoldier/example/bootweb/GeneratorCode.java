package com.github.codingsoldier.example.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.MybatisPlusCodeGenerator;

public class GeneratorCode {

  /**
   * 注意，旧文件不会被覆盖
   */
  public static void main(String[] args) {
    // 数据库url
    MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://127.0.0.1:3306/cpq?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    // 数据库账号
    MybatisPlusCodeGenerator.dbUsername = "root";
    // 数据库密码
    MybatisPlusCodeGenerator.dbPassword = "123456";

    // 项目 main 目录的绝对路径
    MybatisPlusCodeGenerator.srcMainAbsolutePath = "E:\\github\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
    // 项目包名
    MybatisPlusCodeGenerator.parent = "com.github.codingsoldier.example.bootweb";

    // 作者
    MybatisPlusCodeGenerator.author = "cpq";
    // 表名
    MybatisPlusCodeGenerator.tableName = "demo_cudr";

    // 生成代码
    MybatisPlusCodeGenerator.generate();

  }
}