package com.github.codingsoldier.starter.mybatisplus.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * https://baomidou.com/pages/981406/#mapper-%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@SuppressWarnings({"squid:S1104", "squid:S1444"})
public class MybatisPlusCodeGenerator {

    public static final String TABLE_PREFIX = "t_";
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusCodeGenerator.class);
    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String srcMainAbsolutePath;
    public static String parent;
    public static String author;
    public static String tableName;
    public static String templatesDir = "/templates/v3";

    private static GlobalConfig buildGlobalConfig() {
        return new GlobalConfig.Builder()
                .disableOpenDir()
                .outputDir(srcMainAbsolutePath + File.separator + "java")
                .author(author)
                .enableSpringdoc()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd HH:mm:ss")
                .build();
    }

    private static PackageConfig buildPackageConfig() {
        EnumMap<OutputFile, String> pathInfo = new EnumMap<>(OutputFile.class);
        return new PackageConfig.Builder()
                .parent(parent)
                // .moduleName("多加一层目录，例如表名，生成的代码放在表名目录下")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper")
                .controller("controller")
                // .other("")
                .pathInfo(pathInfo)
                .build();
    }

    private static StrategyConfig buildStrategyConfig() {
        return new StrategyConfig.Builder()
                .addInclude(tableName)
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
                .mapperAnnotation(org.apache.ibatis.annotations.Mapper.class)
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
    }

    /**
     * 生成文件
     * 注意：旧文件不会被覆盖
     */
    public static void generate() {

        String tableJavaName = GeneratorUtil.tableJavaName(tableName);

        Map<String, Object> map = new HashMap<>(128);
        Map<String, String> files = new HashMap<>(128);
        map.put("packageDto", parent + ".dto");
        map.put("packageVo", parent + ".vo");

        String addDtoClassName = tableJavaName + "AddDto";
        map.put("addDtoClassName", addDtoClassName);
        files.put(addDtoClassName, templatesDir + "/AddDto.java.ftl");

        String updateDtoClassName = tableJavaName + "UpdateDto";
        map.put("updateDtoClassName", updateDtoClassName);
        files.put(updateDtoClassName, templatesDir + "/UpdateDto.java.ftl");

        String pageQueryDtoClassName = tableJavaName + "PageQueryDto";
        map.put("pageQueryDtoClassName", pageQueryDtoClassName);
        files.put(pageQueryDtoClassName, templatesDir + "/PageQueryDto.java.ftl");


        String detailVoClassName = tableJavaName + "DetailVo";
        map.put("detailVoClassName", detailVoClassName);
        files.put(detailVoClassName, templatesDir + "/DetailVo.java.ftl");

        String pageVoClassName = tableJavaName + "PageVo";
        map.put("pageVoClassName", pageVoClassName);
        files.put(pageVoClassName, templatesDir + "/PageVo.java.ftl");

        String v2 = "/templates/v2";
        if (Objects.equals(templatesDir, v2)) {
            map.put("packageAo", parent + ".ao");

            String addUpdateAoClassName = tableJavaName + "AddUpdateAo";
            map.put("addUpdateAoClassName", addUpdateAoClassName);
            files.put(addUpdateAoClassName, templatesDir + "/AddUpdateAo.java.ftl");
        }

        InjectionConfig injectionConfig = new InjectionConfig.Builder()
                .beforeOutputFile((tableInfo, objectMap) -> {
                    LOGGER.debug("tableInfo = {}", tableInfo);
                    LOGGER.debug("objectMap = {}", objectMap);
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
                .xml("/templates/mapper.xml")
                .controller("/templates/controller.java")
                .build();

        DataSourceConfig dataSource = new DataSourceConfig
                .Builder(dbUrl, dbUsername, dbPassword)
                .build();

        GlobalConfig globalConfig = buildGlobalConfig();

        PackageConfig packageConfig = buildPackageConfig();

        StrategyConfig strategyConfig = buildStrategyConfig();

        AutoGenerator generator = new AutoGenerator(dataSource);
        generator.global(globalConfig);
        generator.packageInfo(packageConfig);
        generator.strategy(strategyConfig);
        generator.injection(injectionConfig);
        generator.template(templateConfig);
        generator.execute(new FreemarkerTemplateEngine());

        LOGGER.info("##### 代码生成完毕 #####");

    }


    @SuppressWarnings("squid:S1075")
    public static void main(String[] args) {

        // 数据库url
        MybatisPlusCodeGenerator.dbUrl = "jdbc:mysql://172.16.84.29:3306/easygo_edge?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true";
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
        MybatisPlusCodeGenerator.tableName = "employee";

        // 生成代码
        MybatisPlusCodeGenerator.generate();


    }
}