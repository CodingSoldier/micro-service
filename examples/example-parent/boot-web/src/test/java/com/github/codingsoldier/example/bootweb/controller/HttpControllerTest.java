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
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/http/params/path/123?msg=信息&name=名字");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("123信息名字")));

        MockHttpServletRequestBuilder reqBuilder2 = MockMvcRequestBuilders.get("/http/params/path/123?name=&msg=信息");
        super.mockMvc.perform(reqBuilder2)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("123信息")));
    }

    @Test
    void modelAttribute() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/http/modelAttribute?age=123&name=名字");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        Matchers.equalTo("junit测试修改名字")));
    }

    @Test
    void body() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/http/body")
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
                .post("/http/time/bar")
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
                .post("/http/time/slash")
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

    /**

     @Test
     void time() throws Exception {
     // 时间戳
     String bodyTimestamp = "{\"id\":12345,\"age\":18,\"name\":\"姓名\",\"date\":1647187201000,\"localDate\":1647187201000,\"localDateTime\":1647187201000,\"offsetDateTime\":1647187201000}";
     MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
     .post("/http/time")
     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
     .content(bodyTimestamp);
     super.mockMvc.perform(reqBuilder)
     .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.date",
     Matchers.equalTo(1647187201000L)));

     // 时间字符串
     String timeString = "{\"id\":12345,\"age\":1212,\"name\":\"姓名\",\"date\":\"2022-01-01 01:02:02\",\"localDate\":\"2022-01-01\",\"localDateTime\":\"2022-01-01 01:02:02\",\"offsetDateTime\":\"2022-01-01 01:02:02\"}";
     reqBuilder = MockMvcRequestBuilders
     .post("/http/time")
     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
     .content(timeString);
     super.mockMvc.perform(reqBuilder)
     .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.date",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTime",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTime",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDate",
     Matchers.equalTo(1640966400000L)));

     // 时间字符串斜杠
     timeString = "{\"id\":12345,\"age\":1212,\"name\":\"姓名\",\"date\":\"2022/01/01 01:02:02\",\"localDate\":\"2022/01/01\",\"localDateTime\":\"2022/01/01 01:02:02\",\"offsetDateTime\":\"2022/01/01 01:02:02\"}";
     reqBuilder = MockMvcRequestBuilders
     .post("/http/time")
     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
     .content(timeString);
     super.mockMvc.perform(reqBuilder)
     .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.date",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTime",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTime",
     Matchers.equalTo(1640970122000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDate",
     Matchers.equalTo(1640966400000L)));
     }

     @Test
     void timeAnno() throws Exception {
     // 时间戳
     String bodyTimestamp = "{\"date\":\"1660555532610\",\"localDate\":\"2022-04-01\",\"localDateTime\":\"1660555532610\",\"offsetDateTime\":\"1660555532610\"}";
     MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
     .post("/http/time/anno")
     .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
     .content(bodyTimestamp);
     super.mockMvc.perform(reqBuilder)
     .andExpect(MockMvcResultMatchers.status().isOk())
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.dateMiddle", Matchers.equalTo("2022-08-15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateMiddle", Matchers.equalTo(1648742400000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTimeMiddle", Matchers.equalTo("2022-08-15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTimeMiddle", Matchers.equalTo("2022-08-15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.dateSlash", Matchers.equalTo("2022/08/15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateSlash", Matchers.equalTo(1648742400000L)))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTimeSlash", Matchers.equalTo("2022/08/15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTimeSlash", Matchers.equalTo("2022/08/15 17:25:32")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.dateMiddleDay", Matchers.equalTo("2022-08-15")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateMiddleDay", Matchers.equalTo("2022-04-01")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTimeMiddleDay", Matchers.equalTo("2022-08-15")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTimeMiddleDay", Matchers.equalTo("2022-08-15")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.dateSlashDay", Matchers.equalTo("2022/08/15")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateSlashDay", Matchers.equalTo("2022/04/01")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.localDateTimeSlashDay", Matchers.equalTo("2022/08/15")))
     .andExpect(MockMvcResultMatchers.jsonPath("$.data.offsetDateTimeSlashDay", Matchers.equalTo("2022/08/15")));
     }
     */

    @Test
    void noWrapper() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/http/no-wrapper");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.equalTo(1)));
    }

    @Test
    void propertiesMsg() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/http/properties/msg");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        Matchers.equalTo("Junit测试使用")));
    }
}