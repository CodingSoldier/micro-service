package com.github.codingsoldier.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void isEndWith() {
        boolean b = StringUtil.isEndWith("adfa。", StringUtil.END_CHAR);
        assertEquals(true, b);
    }
}