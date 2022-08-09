package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/9 17:20
 */
class ExceptionControllerTest extends BaseTest {

    @Test
    void testAppException() throws Exception{
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/ex/app/exception");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.equalTo("测试APP异常")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResponseCodeEnum.BAD_REQUEST.getCode())));
    }

    @Test
    void testException() throws Exception{
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/ex/exception");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResponseCodeEnum.SERVER_ERROR.getCode())));
    }
}