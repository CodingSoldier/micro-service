package com.github.codingsoldier.bootweb;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://baomidou.com/pages/981406/#mapper-%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE
 */
public class MybatisPlusGenerator {

    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String srcMainAbsolutePath;
    public static String parent;
    public static String author;
    public static List<String> tableNames;


    public static final String TABLE_PREFIX = "t_";

    /**
     * 生成文件
     * 注意：旧文件不会被覆盖
     */
    public static void generate() {

        String xmlOutPutDir = srcMainAbsolutePath + File.separator + "resources/mapper";

        DataSourceConfig dataSource = new DataSourceConfig
                .Builder(dbUrl, dbUsername, dbPassword)
                .build();

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .disableOpenDir()
                .outputDir(srcMainAbsolutePath + File.separator + "java")
                .author(author)
                .enableSwagger()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd HH:mm:ss")
                .build();

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.mapperXml, xmlOutPutDir);
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent(parent)
                // .moduleName("modelName")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                // .xml("mapper")
                .controller("controller")
                .other("")
                .pathInfo(pathInfo)
                .build();

        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude(tableNames)
                .addTablePrefix(TABLE_PREFIX)
                .entityBuilder()
                    .idType(IdType.AUTO)
                    .formatFileName("%sEntity")
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
                    .build()
                .mapperBuilder()
                    .enableMapperAnnotation()
                    .enableBaseResultMap()
                    .enableBaseColumnList()
                    .build()
                .serviceBuilder()
                    .formatServiceFileName("%sService")
                    .build()
                .controllerBuilder()
                    .enableHyphenStyle()
                    .enableRestStyle()
                    .build();

        String tableJavaName = MybatisPlusUtil.tableJavaName(tableNames.get(0));

        Map<String, Object> customMap = new HashMap<>();
        customMap.put("packageDetailVo", parent + ".vo");
        customMap.put("DetailVo", tableJavaName + "DetailVo");

        customMap.put("packageAddDto", parent + ".dto");
        customMap.put("AddDto", tableJavaName + "AddDto");

        Map<String, String> customFile = new HashMap<>();
        customFile.put(tableJavaName + "DetailVo.java", "/templates/DetailVo.java.ftl");

        customFile.put(tableJavaName + "AddDto.java", "/templates/AddDto.java.ftl");

        InjectionConfig injectionConfig = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("#######" + tableInfo);
                    System.out.println("#######" + objectMap);
                })
                .customMap(customMap)
                .customFile(customFile)
                .build();


        AutoGenerator generator = new AutoGenerator(dataSource);
        generator.global(globalConfig);
        generator.packageInfo(packageConfig);
        generator.strategy(strategyConfig);
        generator.injection(injectionConfig);
        generator.execute(new CustomFreemarkerTemplateEngine());

    }


    public static void main(String[] args) {

        dbUrl = "jdbc:mysql://localhost:3306/cpq?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=true";
        dbUsername = "root";
        dbPassword = "cpq..123";

        srcMainAbsolutePath = "E:\\github\\example-parent\\example-starter-web\\src\\main";
        parent = "com.github.codingsoldier.examplestarterweb";

        author = "cpq";
        tableNames = Arrays.asList("user_copy1");

        generate();

    }
}