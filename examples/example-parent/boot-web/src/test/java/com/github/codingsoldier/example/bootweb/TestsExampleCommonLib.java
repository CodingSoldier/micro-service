package com.github.codingsoldier.example.bootweb;

import com.github.codingsoldier.example.examplecommon.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TestsExampleCommonLib {

    @Test
    void lib() {
        assertEquals("junit测试信息", Constants.JUNIT_TEST_INFO);
        log.info("#################");
    }

}
