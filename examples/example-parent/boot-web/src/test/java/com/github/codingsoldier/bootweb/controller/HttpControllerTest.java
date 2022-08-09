package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;

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

    public static void main(String[] args) {
        HashMap<String, Object> m = new HashMap<>();
        m.put("id", 122);
        m.put("name", "122");
        m.put("age", 11);
        m.put("phone", "18952145124");
        m.put("dateOfBirth", 1647445294184L);
        String s = ObjectMapperUtil.writeValueAsString(m);
        System.out.println(s);
    }

    @Test
    void body() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/http/body")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{\"phone\":\"18952145124\",\"name\":\"名字\",\"dateOfBirth\":1647445294184,\"id\":122,\"age\":11}");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name",
                        Matchers.equalTo("junit测试修改名字")));
    }

    @Test
    void time() throws Exception {
    }

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