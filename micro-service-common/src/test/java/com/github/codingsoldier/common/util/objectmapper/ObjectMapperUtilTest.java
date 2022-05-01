package com.github.codingsoldier.common.util.objectmapper;


import com.github.codingsoldier.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class ObjectMapperUtilTest {

    @Test
    public void testDateTime(){
        Date date = new Date();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        TestDateTimeBean bean01 = TestDateTimeBean.builder()
                .id(111L).name("名字").age(232)
                .beanDate(date)
                .beanLocalDate(localDate)
                .beanLocalDateTime(localDateTime)
                .offsetDateTime(offsetDateTime)
                .build();
        String beanStr = ObjectMapperUtil.writeValueAsString(bean01);
        log.info("序列化结果: {}", beanStr);
       assertEquals(beanStr, "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":"+date.getTime()+",\"beanLocalDateTime\":"+DateUtil.toTimestamp(localDateTime)+",\"beanLocalDate\":"+DateUtil.toTimestamp(localDate)+",\"offsetDateTime\":"+DateUtil.toTimestamp(offsetDateTime)+"}");

        String str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":1647158971033,\"beanLocalDateTime\":1647158971101,\"beanLocalDate\":1647100800000, \"offsetDateTime\":1647158971101}";
        TestDateTimeBean bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("时间戳结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime(1647158971101L));
       assertEquals(bean1.getOffsetDateTime(), DateUtil.toOffsetDateTime(1647158971101L));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011-01-01\",\"beanLocalDateTime\":\"2010-01-01\",\"beanLocalDate\":\"2012-01-01\", \"offsetDateTime\":\"2012-01-01\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy-MM-dd结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2010-01-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011-01-01 10:04\",\"beanLocalDateTime\":\"2010-01-01 10:04\",\"beanLocalDate\":\"2012-01-01 10:04\",\"offsetDateTime\":\"2012-01-01 10:04\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy-MM-dd HH:mm结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2010-01-01 10:04", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011-01-01 10:04:01\",\"beanLocalDateTime\":\"2010-01-01 10:04:01\",\"beanLocalDate\":\"2012-01-01 10:04:01\", \"offsetDateTime\":\"2012-01-01 10:04:01\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy-MM-dd HH:mm:ss结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2010-01-01 10:04:01", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011-01-01 10:04:01.002\",\"beanLocalDateTime\":\"2010-01-01 10:04:01.002\",\"beanLocalDate\":\"2012-01-01 10:04:01.002\",\"offsetDateTime\":\"2012-01-01 10:04:01.002\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy-MM-dd HH:mm:ss结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2010-01-01 10:04:01.002", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011/01/01\",\"beanLocalDateTime\":\"2011/01/01\",\"beanLocalDate\":\"2011/01/01\", \"offsetDateTime\":\"2011/01/01\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy/MM/dd结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2011/01/01 00:00:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        str = "{\"id\":111,\"age\":232,\"name\":\"名字\",\"beanDate\":\"2011/01/01 10:04:01\",\"beanLocalDateTime\":\"2011/01/01 10:04:01\",\"beanLocalDate\":\"2011/01/01 10:04:01\", \"offsetDateTime\":\"2011/01/01 10:04:01\"}";
        bean1 = ObjectMapperUtil.readValue(str, TestDateTimeBean.class);
        log.info("yyyy/MM/dd HH:mm:ss结果：{}", bean1);
       assertEquals(bean1.getBeanLocalDateTime(), DateUtil.toLocalDateTime("2011/01/01 10:04:01", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
    }

}