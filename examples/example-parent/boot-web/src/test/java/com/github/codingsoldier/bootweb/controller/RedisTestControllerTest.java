package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author chenpq05
 * @since 2022/8/9 17:05
 */
class RedisTestControllerTest extends BaseTest {

    @Test
    void test1() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/redis/test");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResponseCodeEnum.SUCCESS.getCode())));
    }
}