package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Slf4j
public class DateUtilTest {

    @Test
    public void testDateTime(){

        Assert.assertEquals(DateUtil.toLocalDate(1647187261000L),"2022-03-14");
        Assert.assertEquals(DateUtil.toLocalDateTime(1647187261000L),"2022-03-14T00:01:01");
        Assert.assertEquals(DateUtil.toOffsetDateTime(1647187261000L),"2022-03-14T00:01:01+08:00");

        Assert.assertEquals(DateUtil.toLocalDate(1647187261000L),"2022-03-14");
        Assert.assertEquals(DateUtil.toLocalDateTime(1647273541000L),"2022-03-14T23:59:01");
        Assert.assertEquals(DateUtil.toOffsetDateTime(1647273541000L),"2022-03-14T23:59:01+08:00");

        long currentTimeMillis = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        Assert.assertTrue(currentTimeMillis - 2L < DateUtil.toTimestamp(localDateTime));
        Assert.assertTrue(currentTimeMillis + 2L < DateUtil.toTimestamp(localDateTime));
        Assert.assertTrue(currentTimeMillis - 2L < DateUtil.toTimestamp(offsetDateTime));
        Assert.assertTrue(currentTimeMillis + 2L < DateUtil.toTimestamp(offsetDateTime));

    }

}