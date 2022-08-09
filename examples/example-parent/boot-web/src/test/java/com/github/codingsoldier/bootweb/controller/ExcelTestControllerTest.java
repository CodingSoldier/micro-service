package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/9 16:49
 */
class ExcelTestControllerTest extends BaseTest {

    @Test
    void download() throws Exception{
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/excel/download");
        super.mockMvc.perform(reqBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}