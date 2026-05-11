package com.github.codingsoldier.common.util.objectmapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.LocalDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.deserializer.OffsetDateTimeDeserializer;
import com.github.codingsoldier.common.util.objectmapper.serializer.TimeToTimestampSerializer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

/**
 * ObjectMapper工具类
 *
 * @author chenpq05
 * @since 2022/2/21 15:02
 */
@Slf4j
public class ObjectMapperUtil {

  private static final ObjectMapper OBJECT_MAPPER = newObjectMapper();

  private ObjectMapperUtil() {
  }

  /**
   * 创建一个新的ObjectMapper
   *
   * @return ObjectMapper
   */
  public static ObjectMapper newObjectMapper() {
    // 时间转换为时间戳。
    SimpleModule timeModule = new SimpleModule();
    TimeToTimestampSerializer timeToTimestampSerializer = new TimeToTimestampSerializer();
    timeModule.addSerializer(Date.class, timeToTimestampSerializer);
    timeModule.addSerializer(LocalDate.class, timeToTimestampSerializer);
    timeModule.addSerializer(LocalDateTime.class, timeToTimestampSerializer);
    timeModule.addSerializer(OffsetDateTime.class, timeToTimestampSerializer);
    timeModule.addDeserializer(Date.class,
        new com.github.codingsoldier.common.util.objectmapper.deserializer.DateDeserializer());
    timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
    timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
    timeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
    return JsonMapper.builder()
        .changeDefaultPropertyInclusion(
            inclusion -> inclusion.withValueInclusion(JsonInclude.Include.ALWAYS))
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .changeDefaultVisibility(
            visibility -> visibility.withVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY))
        .addModule(timeModule)
        .build();
  }


  /**
   * 字符串转对象
   *
   * @param content   content
   * @param valueType valueType
   * @param <T>       T
   * @return T
   */
  public static <T> T readValue(String content, Class<T> valueType) {
    if (StringUtils.isBlank(content)) {
      return null;
    }
    if (String.class.equals(valueType)) {
      return (T) content;
    }
    try {
      return OBJECT_MAPPER.readValue(content, valueType);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return null;
  }

  /**
   * 字节数组转对象
   *
   * @param src       src
   * @param valueType src
   * @return T
   */
  public static <T> T readValue(byte[] src, Class<T> valueType) {
    if (Objects.isNull(src)) {
      return null;
    }
    if (String.class.equals(valueType)) {
      return (T) new String(src, StandardCharsets.UTF_8);
    }
    try {
      return OBJECT_MAPPER.readValue(src, valueType);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return null;
  }

  /**
   * 字符串转对象
   *
   * @param content      content
   * @param valueTypeRef valueTypeRef
   * @param <T>          T
   * @return T
   */
  public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
    if (StringUtils.isBlank(content)) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(content, valueTypeRef);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return null;
  }

  /**
   * 对象转string
   *
   * @param value value
   * @return String
   */
  public static String writeValueAsString(Object value) {
    if (Objects.isNull(value)) {
      return null;
    } else if (value instanceof String) {
      return value.toString();
    }
    try {
      return OBJECT_MAPPER.writeValueAsString(value);
    } catch (Exception e) {
      log.error("异常", e);
    }
    return "";
  }


}
