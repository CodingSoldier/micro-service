package com.github.codingsoldier.bootweb;


import com.github.codingsoldier.starter.mybatisplus.generator.MybatisPlusCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneratorCode {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorCode.class);

    /**
     * 注意，旧文件不会被覆盖
     */
    public static void main(String[] args) {

        LOGGER.info("打印信息吗");


        // 数据库url
        // MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://172.16.84.29:3306/2_3_aface_material?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
        MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://172.16.84.29:3306/easygo_edge?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
        // 数据库账号
        MybatisPlusCodeGenerator.dbUsername = "easygo";
        // 数据库密码
        MybatisPlusCodeGenerator.dbPassword = "Vr@Dev123";

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