package com.github.codingsoldier.starter.mybatisplus.generator;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.apache.ibatis.annotations.Mapper;

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
  public static String dbUrl;
  public static String dbUsername;
  public static String dbPassword;
  public static String srcMainAbsolutePath;
  public static String parent;
  public static String author;
  public static String tableName;
  public static String templatesDir = "/templates/v1";


  /**
   * 生成文件 注意：旧文件不会被覆盖
   */
  public static void generate() {

    BiConsumer<Function<String, String>, GlobalConfig.Builder> globalConfig = (scanner, builder) ->
      builder.disableOpenDir()
          .outputDir(srcMainAbsolutePath + "/java")
          .author(author)
          .enableSpringdoc()
          .dateType(DateType.TIME_PACK)
          .commentDate("yyyy-MM-dd HH:mm:ss")
          .build();

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

    BiConsumer<Function<String, String>, StrategyConfig.Builder> strategyConfig = (scanner, builder) ->
      builder.addInclude(tableName)
          .addTablePrefix(TABLE_PREFIX)
          .entityBuilder()
          .idType(IdType.ASSIGN_ID)
          .enableChainModel()
          .enableLombok()
          .logicDeleteColumnName("deleted")
          .logicDeletePropertyName("deleted")
          .naming(NamingStrategy.underline_to_camel)
          .javaTemplate(templatesDir + "/entity.java")
          .addTableFills(new Column("created_time", FieldFill.INSERT),
              new Column("updated_time", FieldFill.INSERT_UPDATE))
          .build()
          .mapperBuilder()
          .mapperAnnotation(Mapper.class)
          .mapperTemplate(templatesDir + "/mapper.java")
          .mapperXmlTemplate(templatesDir + "/mapper.xml")
          .build()
          .serviceBuilder()
          .serviceImplTemplate(templatesDir + "/service.java")
          .convertServiceImplFileName(tableInfo -> tableInfo + "Service")
          .disableService()
          .build()
          .controllerBuilder()
          .template(templatesDir + "/controller.java")
          .enableHyphenStyle()
          .enableRestStyle()
          .build();

    BiConsumer<Function<String, String>, InjectionConfig.Builder> injectionConfig = (scanner, builder) -> {
      String tableJavaName = GeneratorUtil.tableJavaName(tableName);
      String addDTOClassName = tableJavaName + "AddDTO";
      String updateDTOClassName = tableJavaName + "UpdateDTO";
      String pageQueryDTOClassName = tableJavaName + "PageQueryDTO";
      String detailVOClassName = tableJavaName + "DetailVO";
      String pageVOClassName = tableJavaName + "PageVO";

      // 自定义参数
      Map<String, Object> customMap = HashMap.newHashMap(16);
      Map<String, Object> customParam = HashMap.newHashMap(64);
      customMap.put("customParam", customParam);
      customParam.put("packageDTO", parent + ".dto");
      customParam.put("packageVO", parent + ".vo");
      customParam.put("addDTOClassName", addDTOClassName);
      customParam.put("updateDTOClassName", updateDTOClassName);
      customParam.put("pageQueryDTOClassName", pageQueryDTOClassName);
      customParam.put("detailVOClassName", detailVOClassName);
      customParam.put("pageVOClassName", pageVOClassName);

      // 自定义文件
      CustomFile addDTOFile = new CustomFile.Builder().fileName(addDTOClassName)
          .templatePath(templatesDir + "/AddDTO.java.ftl").build();
      CustomFile updateDTOFile = new CustomFile.Builder().fileName(updateDTOClassName)
          .templatePath(templatesDir + "/UpdateDTO.java.ftl").build();
      CustomFile pageQueryDTOFile = new CustomFile.Builder().fileName(pageQueryDTOClassName)
          .templatePath(templatesDir + "/PageQueryDTO.java.ftl").build();
      CustomFile detailDTOFile = new CustomFile.Builder().fileName(detailVOClassName)
          .templatePath(templatesDir + "/DetailVO.java.ftl").build();
      CustomFile pageVOFile = new CustomFile.Builder().fileName(pageVOClassName)
          .templatePath(templatesDir + "/PageVO.java.ftl").build();
      List<CustomFile> fileList = List.of(addDTOFile, updateDTOFile, pageQueryDTOFile,
          detailDTOFile, pageVOFile);

      builder.customMap(customMap)
          .customFile(fileList)
          .build();
    };

    FastAutoGenerator.create(dbUrl, dbUsername, dbPassword)
        .globalConfig(globalConfig)
        .packageConfig(packageConfig)
        .strategyConfig(strategyConfig)
        .injectionConfig(injectionConfig)
        .templateEngine(new CustomFreemarkerTemplateEngine())
        .execute();

  }


}