package com.github.codingsoldier.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(1660400854179L, CommonUtil.parseLong("1660400854179", -1));
        assertEquals(1660400854179L, CommonUtil.parseLong("1660400854179"));
    }

    @Test
    void parseInteger() {
        assertEquals(100, CommonUtil.parseInteger("100"));
    }


    @Test
    void parseInt() {
        assertEquals(100, CommonUtil.parseInt("100", -1));
    }

}