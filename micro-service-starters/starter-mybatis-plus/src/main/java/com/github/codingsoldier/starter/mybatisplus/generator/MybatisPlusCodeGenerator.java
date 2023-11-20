package com.github.codingsoldier.starter.mybatisplus.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 * https://baomidou.com/pages/981406/#mapper-%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@SuppressWarnings({"squid:S1104", "squid:S1444"})
public class MybatisPlusCodeGenerator {

    private MybatisPlusCodeGenerator() {
    }

    public static final String TABLE_PREFIX = "t_";
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusCodeGenerator.class);
    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String srcMainAbsolutePath;
    public static String parent;
    public static String author;
    public static String tableName;
    public static String templatesDir = "/templates/v1";

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
        map.put("packageDTO", parent + ".dto");
        map.put("packageVO", parent + ".vo");

        String addDTOClassName = tableJavaName + "AddDTO";
        map.put("addDTOClassName", addDTOClassName);
        files.put(addDTOClassName, templatesDir + "/AddDTO.java.ftl");

        String updateDTOClassName = tableJavaName + "UpdateDTO";
        map.put("updateDTOClassName", updateDTOClassName);
        files.put(updateDTOClassName, templatesDir + "/UpdateDTO.java.ftl");

        String pageQueryDTOClassName = tableJavaName + "PageQueryDTO";
        map.put("pageQueryDTOClassName", pageQueryDTOClassName);
        files.put(pageQueryDTOClassName, templatesDir + "/PageQueryDTO.java.ftl");


        String detailVOClassName = tableJavaName + "DetailVO";
        map.put("detailVOClassName", detailVOClassName);
        files.put(detailVOClassName, templatesDir + "/DetailVO.java.ftl");

        String pageVOClassName = tableJavaName + "PageVO";
        map.put("pageVOClassName", pageVOClassName);
        files.put(pageVOClassName, templatesDir + "/PageVO.java.ftl");

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
                .entity(templatesDir + "/entity.java")
                .service(templatesDir + "/service.java")
                .serviceImpl(templatesDir + "/serviceImpl.java")
                .mapper(templatesDir + "/mapper.java")
                .xml(templatesDir + "/mapper.xml")
                .controller(templatesDir + "/controller.java")
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
        generator.execute(new CustomFreemarkerTemplateEngine());

    }


}