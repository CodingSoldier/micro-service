package com.github.codingsoldier.common.util;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolUtilTest {

    @Test
    void execute() {
        ThreadPoolUtil.execute(() -> assertTrue(true));
    }

    @Test
    void submit() throws Exception{
        Future<String> resp = ThreadPoolUtil.submit(() -> "返回结果");
        assertEquals("返回结果", resp.get());
    }
}