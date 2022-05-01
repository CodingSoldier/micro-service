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
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent(parent)
                // .moduleName("多加一层目录，例如表名，生成的代码放在表名目录下")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper")
                .controller("controller")
                .other("")
                .pathInfo(pathInfo)
                .build();

        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude(tableNames)
                .addTablePrefix(TABLE_PREFIX)
                .entityBuilder()
                    .idType(IdType.AUTO)
                    // .formatFileName("%sEntity")
                    .enableChainModel()
                    .enableLombok()
                    .logicDeleteColumnName("deleted")
                    .logicDeletePropertyName("deleted")
                    .naming(NamingStrategy.underline_to_camel)
                    .addTableFills(new Column("created_by", FieldFill.INSERT),
                        new Column("created_time", FieldFill.INSERT),
                        new Column("updated_by", FieldFill.INSERT_UPDATE),
                        new Column("updated_time", FieldFill.INSERT_UPDATE))
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

        Map<String, Object> map = new HashMap<>();
        Map<String, String> files = new HashMap<>();
        map.put("packageDto", parent + ".dto");
        map.put("packageVo", parent + ".vo");
        map.put("packageAo", parent + ".ao");


        String addDtoClassName = tableJavaName + "AddDto";
        map.put("addDtoClassName", addDtoClassName);
        files.put(addDtoClassName, "/templates/AddDto.java.ftl");

        String updateDtoClassName = tableJavaName + "UpdateDto";
        map.put("updateDtoClassName", updateDtoClassName);
        files.put(updateDtoClassName, "/templates/UpdateDto.java.ftl");

        String detailVoClassName = tableJavaName + "DetailVo";
        map.put("detailVoClassName", detailVoClassName);
        files.put(detailVoClassName, "/templates/DetailVo.java.ftl");

        String aoClassName = tableJavaName + "Ao";
        map.put("aoClassName", aoClassName);
        files.put(aoClassName, "/templates/Ao.java.ftl");

        InjectionConfig injectionConfig = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    System.out.println("$$ tableInfo $$$ " + tableInfo);
                    System.out.println("### tableInfo ####" + objectMap);
                })
                .customMap(map)
                .customFile(files)
                .build();

        TemplateConfig templateConfig = new TemplateConfig.Builder()
                .disable(TemplateType.ENTITY)
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .mapperXml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();

        AutoGenerator generator = new AutoGenerator(dataSource);
        generator.global(globalConfig);
        generator.packageInfo(packageConfig);
        generator.strategy(strategyConfig);
        generator.injection(injectionConfig);
        generator.template(templateConfig);
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