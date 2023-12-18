package com.github.codingsoldier.common.util.thread;

import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolUtilTest {

    @Test
    void execute() {
        ThreadPoolUtil.execute(() -> assertTrue(true));
    }

    @Test
    void getExecutor() {
        assertNotNull(ThreadPoolUtil.getExecutor());
    }

    @Test
    void executeException() {
        ThreadPoolUtil.execute(() -> {
            throw new HttpStatus5xxException("线程池中抛出异常");
        });
    }

    @Test
    void submit() throws Exception{
        Future<String> resp = ThreadPoolUtil.submit(() -> "返回结果");
        assertEquals("返回结果", resp.get());
    }

}