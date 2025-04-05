package com.github.codingsoldier.starter.mybatisplus.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 代码生成器
 * <a href="https://baomidou.com/pages/981406/#mapper-%E7%AD%96%E7%95%A5%E9%85%8D%E7%BD%AE">...</a>
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

        BiConsumer<Function<String, String>, GlobalConfig.Builder> globalConfig = (scanner, builder) -> {
            builder.disableOpenDir()
                    .outputDir(srcMainAbsolutePath + "/java")
                    .author(author)
                    .enableSpringdoc()
                    .dateType(DateType.TIME_PACK)
                    .commentDate("yyyy-MM-dd HH:mm:ss")
                    .build();
        };

        BiConsumer<Function<String, String>, PackageConfig.Builder> packageConfig = (scanner, builder) -> {
            EnumMap<OutputFile, String> pathInfo = new EnumMap<>(OutputFile.class);
            builder.parent(parent)
                    .serviceImpl("service")
                    .mapper("mapper")
                    .xml("mapper")
                    .controller("controller")
                    .pathInfo(pathInfo)
                    .build();
        };

        BiConsumer<Function<String, String>, StrategyConfig.Builder> createTime = (scanner, builder) -> {
            builder.addInclude(tableName)
                    .addTablePrefix(TABLE_PREFIX)
                    .entityBuilder()
                    .idType(IdType.AUTO)
                    .enableChainModel()
                    .enableLombok()
                    .logicDeleteColumnName("deleted")
                    .logicDeletePropertyName("deleted")
                    .naming(NamingStrategy.underline_to_camel)
                    .javaTemplate(templatesDir + "/entity.java")
                    .addTableFills(new Column("created_by", FieldFill.INSERT),
                            new Column("created_time", FieldFill.INSERT),
                            new Column("updated_by", FieldFill.INSERT_UPDATE),
                            new Column("updated_time", FieldFill.INSERT_UPDATE))
                    .build()
                    .mapperBuilder()
                    .mapperAnnotation(org.apache.ibatis.annotations.Mapper.class)
                    .mapperTemplate(templatesDir + "/mapper.java")
                    .mapperXmlTemplate(templatesDir + "/mapper.xml")
                    .build()
                    .serviceBuilder()
                    .serviceImplTemplate(templatesDir + "/serviceImpl.java")
                    .disableService()
                    .build()
                    .controllerBuilder()
                    .template(templatesDir + "/controller.java")
                    .enableHyphenStyle()
                    .enableRestStyle()
                    .build();
        };

        BiConsumer<Function<String, String>, InjectionConfig.Builder> injectionConfig = (scanner, builder) -> {
            // CustomFile build = new CustomFile.Builder().fileName("Dto.java").templatePath("/template/dto.java.vm")

            //         .packageName(modelPackagePrefix + "dto").enableFileOverride().build();
            // consumer.customFile(
            //         Arrays.asList(
            //                 build,
            //                 new CustomFile.Builder().fileName("QueryDto.java").templatePath("/template/queryDto.java.vm")
            //                         .packageName(modelPackagePrefix + "dto").enableFileOverride().build(),
            //                 new CustomFile.Builder().fileName("Vo.java").templatePath("/template/vo.java.vm")
            //                         .packageName(modelPackagePrefix + "vo").enableFileOverride().build()
            //         ));

            builder.beforeOutputFile((tableInfo, objectMap) -> {
                        LOGGER.debug("tableInfo = {}", tableInfo);
                        LOGGER.debug("objectMap = {}", objectMap);
                    })
                    .customMap(map)
                    .customFile(files)
                    .build();
        };

        FastAutoGenerator.create(dbUrl, dbUsername, dbPassword)
                .globalConfig(globalConfig)
                .packageConfig(packageConfig)
                .strategyConfig(createTime)
                .injectionConfig(injectionConfig)
                .templateEngine(new CustomFreemarkerTemplateEngine())
                .execute();

    }


}