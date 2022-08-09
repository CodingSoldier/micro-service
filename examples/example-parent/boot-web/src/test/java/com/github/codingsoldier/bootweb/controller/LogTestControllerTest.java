package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author chenpq05
 * @since 2022/8/9 11:12
 */
class LogTestControllerTest extends BaseTest {

    @Test
    void testPrint() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/test/print?str=success");
        super.mockMvc.perform(reqBuilder)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasKey("code")))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasKey("message")))
            .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasKey("data")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                    Matchers.equalTo(ResponseCodeEnum.SUCCESS.getCode())));
    }

}