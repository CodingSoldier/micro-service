package com.github.codingsoldier.startermybatisplus;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * https://baomidou.com/pages/981406/#mapper-%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE
 */
public class CodeGenerator {

    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String srcMainAbsolutePath;
    public static String parent;
    public static String author;
    public static List<String> tableNames;

    /**
     * 生成文件
     * 注意：旧文件不会被覆盖
     */
    public static void generate() {

        String xmlOutPutDir = srcMainAbsolutePath + File.separator + "resources/mapper";

        DataSourceConfig.Builder dbConfigBuilder = new DataSourceConfig
                .Builder(dbUrl, dbUsername, dbPassword);

        FastAutoGenerator.create(dbConfigBuilder)
                .globalConfig(builder -> {
                    builder.disableOpenDir()
                            .outputDir(srcMainAbsolutePath + File.separator + "java")
                            .author(author)
                            .enableSwagger()
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss");
                })
                .packageConfig(builder -> {
                    builder.parent(parent)
                            // .moduleName("modelName")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            // .xml("mapper")
                            .controller("controller")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, xmlOutPutDir));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableNames)
                            .addTablePrefix("t_")
                            .entityBuilder()
                            .enableChainModel()
                            .enableLombok()
                            // .enableTableFieldAnnotation()
                            .logicDeleteColumnName("deleted")
                            .logicDeletePropertyName("deleted")
                            .naming(NamingStrategy.underline_to_camel)
                            .addTableFills(new Column("create_id", FieldFill.INSERT),
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_id", FieldFill.INSERT_UPDATE),
                                    new Column("update_time", FieldFill.INSERT_UPDATE))
                            .idType(IdType.AUTO)
                            .formatFileName("%sEntity")
                            .mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .controllerBuilder()
                            .enableHyphenStyle()
                            .enableRestStyle();
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}