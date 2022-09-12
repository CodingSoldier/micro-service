package com.github.codingsoldier.starter.mybatisplus.generator;

/**
 * 生成代码
 * @author chenpq05
 * @since 2022/8/18 17:00
 */
@SuppressWarnings("squid:S2187")
class MybatisPlusCodeGeneratorTest {

    /**
     * 注意，旧文件不会被覆盖
     */
    public static void main(String[] args) {
        // 数据库url
        MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://localhost:3306/cpq?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        // 数据库账号
        MybatisPlusCodeGenerator.dbUsername = "root";
        // 数据库密码
        MybatisPlusCodeGenerator.dbPassword = "cpq..123";

        // 项目 main 目录的绝对路径
        MybatisPlusCodeGenerator.srcMainAbsolutePath = "E:\\github\\micro-service\\examples\\example-parent\\boot-web\\src\\main";
        // 项目包名
        MybatisPlusCodeGenerator.parent = "com.github.codingsoldier.temp111";

        // 作者
        MybatisPlusCodeGenerator.author = "cpq";
        // 表名
        MybatisPlusCodeGenerator.tableName = "user";

        // 生成代码
        MybatisPlusCodeGenerator.generate();

    }
}