package com.github.codingsoldier.starter.web.util;

import com.github.codingsoldier.common.exception.BackendServicesException;
import com.github.codingsoldier.common.util.thread.ThreadPoolUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThreadPoolUtilTest {

    @Test
    void execute() {
        ThreadPoolUtil.execute(() -> assertTrue(true));
    }

    @Test
    void executeException() {
        ThreadPoolUtil.execute(() -> {
            throw new BackendServicesException("线程池中抛出异常");
        });
    }

    @Test
    void submit() throws Exception{
        Future<String> resp = ThreadPoolUtil.submit(() -> "返回结果");
        assertEquals("返回结果", resp.get());
    }
}