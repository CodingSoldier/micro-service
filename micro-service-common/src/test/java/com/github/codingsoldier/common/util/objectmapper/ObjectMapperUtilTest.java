package com.github.codingsoldier.common.util.objectmapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.codingsoldier.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/8 16:35
 */
@Slf4j
class ObjectMapperUtilTest {

    @Test
    void newObjectMapper() {
        ObjectMapper objectMapper = ObjectMapperUtil.newObjectMapper();
        assertNotEquals(null, objectMapper, "异常：ObjectMapperUtil.newObjectMapper() == null");
    }

    @Test
    void readValue() {
        String n = null;
        Map testNull = ObjectMapperUtil.readValue(n, Map.class);
        assertEquals(null, testNull);

        String testStr = ObjectMapperUtil.readValue("测试字符串", String.class);
        assertEquals("测试字符串", testStr);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 111);
        map.put("age", 232);
        map.put("name", "名字");
        String mapStr = "{\"id\":111,\"age\":232,\"name\":\"名字\"}";
        Map readMap = ObjectMapperUtil.readValue(mapStr, Map.class);
        assertEquals(map.get("id"), readMap.get("id"));
        assertEquals(map.get("age"), readMap.get("age"));
        assertEquals(map.get("name"), readMap.get("name"));
    }

    @Test
    void testReadValue() {
        byte[] n = null;
        Map testNull = ObjectMapperUtil.readValue(n, Map.class);
        assertEquals(null, testNull);

        String testStr = ObjectMapperUtil.readValue("测试字符串".getBytes(StandardCharsets.UTF_8), String.class);
        assertEquals("测试字符串", testStr);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 111);
        map.put("age", 232);
        map.put("name", "名字");
        String mapStr = "{\"id\":111,\"age\":232,\"name\":\"名字\"}";
        Map readMap = ObjectMapperUtil.readValue(mapStr.getBytes(StandardCharsets.UTF_8), Map.class);
        assertEquals(map.get("id"), readMap.get("id"));
        assertEquals(map.get("age"), readMap.get("age"));
        assertEquals(map.get("name"), readMap.get("name"));
    }

    @Test
    void testReadValue1() {
        String n = "";
        Map testNull = ObjectMapperUtil.readValue(n, new TypeReference<Map>() {
        });
        assertEquals(null, testNull);

        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 111);
        map.put("age", 232);
        map.put("name", "名字");
        String mapStr = "{\"id\":111,\"age\":232,\"name\":\"名字\"}";
        Map<String, Object> readMap = ObjectMapperUtil.readValue(mapStr, new TypeReference<Map<String, Object>>() {
        });
        assertEquals(map.get("id"), readMap.get("id"));
        assertEquals(map.get("age"), readMap.get("age"));
        assertEquals(map.get("name"), readMap.get("name"));
    }

    @Test
    void writeValueAsString() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 111);
        map.put("age", 232);
        map.put("name", "名字");
        String str = ObjectMapperUtil.writeValueAsString(map);
        assertTrue(str.contains("\"name\":\"名字\""));
        assertTrue(str.contains("\"id\":111"));
        assertTrue(str.contains("\"age\":232"));
    }

    @Test
    void testReadWrite() {
        Date date = new Date(1660011886928L);
        LocalDate localDate = LocalDate.of(2022, 01, 01);
        LocalDateTime localDateTime = LocalDateTime.of(2022, 01, 01, 01, 01, 02);
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDate, LocalTime.of(01, 01, 02), ZoneOffset.of("+8"));
        TestDateTimeBean bean01 = TestDateTimeBean.builder()
                .id(111L).name("名字").age(232)
                .beanDate(date)
                .beanLocalDate(localDate)
                .beanLocalDateTime(localDateTime)
                .offsetDateTime(offsetDateTime)
                .build();
        String beanStr = ObjectMapperUtil.writeValueAsString(bean01);
        log.info("序列化结果: {}", beanStr);
        assertEquals("{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":1660011886928,\"beanLocalDateTime\":1640970062000,\"beanLocalDate\":1640966400000,\"offsetDateTime\":1640970062000}", beanStr);

        TestDateTimeBean readBean = ObjectMapperUtil.readValue(beanStr, TestDateTimeBean.class);
        assertEquals(date, readBean.getBeanDate());
        assertEquals(localDate, readBean.getBeanLocalDate());
        assertEquals(localDateTime, readBean.getBeanLocalDateTime());
        assertEquals(offsetDateTime, readBean.getOffsetDateTime());

    }
}