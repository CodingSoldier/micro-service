package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CommonUtilTest {

    @Test
    void uuid32() {
        assertEquals(32, CommonUtil.uuid32().length());
    }

    @Test
    void strNotEqual() {
        assertEquals(false, CommonUtil.strNotEqual(null, null));
        log.info("{}", CommonUtil.strNotEqual(null, null));

        assertEquals(true, CommonUtil.strNotEqual(null, ""));
        log.info("{}", CommonUtil.strNotEqual(null, ""));

        assertEquals(true, CommonUtil.strNotEqual("", null));
        log.info("{}", CommonUtil.strNotEqual("", null));

        assertEquals(false, CommonUtil.strNotEqual("", ""));
        log.info("{}", CommonUtil.strNotEqual("", ""));

        assertEquals(false, CommonUtil.strNotEqual("aa11", "aa11"));
        log.info("{}", CommonUtil.strNotEqual("aa11", "aa11"));

        assertEquals(true, CommonUtil.strNotEqual("aa", "a"));
        log.info("{}", CommonUtil.strNotEqual("aa", "a"));
    }

    @Test
    void objToStr() {
        assertEquals("", CommonUtil.objToStr(null));
        assertEquals("aa", CommonUtil.objToStr("aa"));
        assertEquals("123", CommonUtil.objToStr(123));
    }

    @Test
    void parseLong() {
        assertEquals(1660400854179L, CommonUtil.parseLong("1660400854179"));
    }

    @Test
    void parseInteger() {
        assertEquals(100, CommonUtil.parseLong("100"));
    }

    @Test
    void split() {
        List<String> strArr = CommonUtil.split("a, bc,de", ",", String.class);
        assertEquals("a", strArr.get(0));
        assertEquals("bc", strArr.get(1));
        assertEquals("de", strArr.get(2));

        List<Integer> intArr = CommonUtil.split("111, 232,3434", ",", Integer.class);
        assertEquals(111, intArr.get(0));
        assertEquals(232, intArr.get(1));
        assertEquals(3434, intArr.get(2));

        List<Long> longArr = CommonUtil.split("1660400854179, 1660400854172,1660200854179", ",", Long.class);
        assertEquals(1660400854179L, longArr.get(0));
        assertEquals(1660400854172L, longArr.get(1));
        assertEquals(1660200854179L, longArr.get(2));

    }
}