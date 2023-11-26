package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.example.bootweb.BaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author chenpq05
 * @since 2022/8/9 17:38
 */
class HttpControllerTest extends BaseTest {

    @Test
    void paramsPath() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/basic/params/path/123?msg=信息&name=名字");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("123信息名字")));

        MockHttpServletRequestBuilder reqBuilder2 = MockMvcRequestBuilders.get("/basic/params/path/123?name=&msg=信息");
        super.mockMvc.perform(reqBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("123信息")));
    }

    @Test
    void modelAttribute() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/basic/modelAttribute?age=123&name=名字");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.records[0].name",
                        Matchers.equalTo("junit测试修改名字")));
    }

    @Test
    void body() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/basic/body")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{\"phone\":\"18952145124\",\"name\":\"名字\",\"dateOfBirth\":\"2022-01-01 01:02:02\",\"id\":122,\"age\":11}");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        Matchers.equalTo("junit测试修改名字")));
    }

    @Test
    void timeBar() throws Exception {
        // 时间字符串
        String timeString = "{\"id\":12345,\"age\":1212,\"name\":\"姓名\",\"date\":\"2022-01-01 01:02:02\",\"localDate\":\"2022-01-01\",\"localDateTime\":\"2022-01-01 01:02:02\"}";
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/basic/time/bar")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(timeString);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.date",
                        Matchers.equalTo("2022-01-01 01:02:02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTime",
                        Matchers.equalTo("2022-01-01 01:02:02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDate",
                        Matchers.equalTo("2022-01-01")));
    }

    @Test
    void timeSlash() throws Exception {
        // 时间字符串
        String timeString = "{\"id\":12345,\"age\":1212,\"name\":\"姓名\",\"date\":\"2022/01/01 01:02:02\",\"localDate\":\"2022/01/01\",\"localDateTime\":\"2022/01/01 01:02:02\"}";
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/basic/time/slash")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(timeString);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.date",
                        Matchers.equalTo("2022/01/01 01:02:02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTime",
                        Matchers.equalTo("2022/01/01 01:02:02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDate",
                        Matchers.equalTo("2022/01/01")));
    }

    @Test
    void noWrapper() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/basic/no-wrapper");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.equalTo(1)));
    }

    @Test
    void propertiesMsg() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/basic/properties/msg");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("Junit测试使用")));
    }
}