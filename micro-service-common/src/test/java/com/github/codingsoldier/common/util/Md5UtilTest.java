package com.github.codingsoldier.common.util;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class Md5UtilTest {

    @Test
    void md5() {
        String origin = "1234567890";
        String result32 = Md5Util.md5(origin, "utf-8");
        assertEquals("e807f1fcf82d132f9bb018ca6738a19f", result32);
    }

    @Test
    void testMd5() {
        String origin = "1234567890";
        String result32 = Md5Util.md5(origin.getBytes(StandardCharsets.UTF_8));
        assertEquals("e807f1fcf82d132f9bb018ca6738a19f", result32);
    }

    @Test
    void testMd51() {
        String origin = "1234567890";
        String result32 = Md5Util.md5(origin);
        assertEquals("e807f1fcf82d132f9bb018ca6738a19f", result32);
    }

}