package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.example.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResultCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


class ValidatedControllerTest extends BaseTest {

    @Test
    void bean() throws Exception {

        String bodyError = "{\"userName\":\"超过长度的名字1223354646\",\"age\":0,\"strList\":[\"错误值\"],\"validation2Dto\":{\"jobName\":\"\"}}";
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/validated/bean")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyError);
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位id不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("列表元素不正确（a-原始、b-续签、c-补充）")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位名称不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("年龄必须大于10")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("用户id不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位名称长度必须是2~10位")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("用户姓名长度必须是2~10位")));

        String bodyTrue = "{\"userId\":11,\"userName\":\"用户名\",\"age\":150,\"strList\":[\"a\",\"b\"],\"validation2Dto\":{\"jobId\":1,\"jobName\":\"工作\"}}";
        reqBuilder = MockMvcRequestBuilders
                .post("/validated/bean")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyTrue);
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                    Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void validBean() throws Exception {

        String bodyError = "{\"userName\":\"超过长度的名字1223354646\",\"age\":0,\"strList\":[\"错误值\"],\"validation2Dto\":{\"jobName\":\"\"}}";
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/validated/valid/bean")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyError);
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位id不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("列表元素不正确（a-原始、b-续签、c-补充）")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位名称不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("年龄必须大于10")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("用户id不能为空")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("岗位名称长度必须是2~10位")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                    Matchers.containsString("用户姓名长度必须是2~10位")));

        String bodyTrue = "{\"userId\":11,\"userName\":\"用户名\",\"age\":150,\"strList\":[\"a\",\"b\"],\"validation2Dto\":{\"jobId\":1,\"jobName\":\"工作\"}}";
        reqBuilder = MockMvcRequestBuilders
                .post("/validated/valid/bean")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyTrue);
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                    Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void beanMethod() throws Exception {
        String bodyError = "{\"userName\":\"超过长度的名字1223354646\",\"age\":0,\"strList\":[\"错误值\"],\"validation2Dto\":{\"jobName\":\"\"}}";
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/validated/bean-method")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyError);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("岗位id不能为空")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("列表元素不正确（a-原始、b-续签、c-补充）")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("岗位名称不能为空")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("年龄必须大于10")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("用户id不能为空")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("岗位名称长度必须是2~10位")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("用户姓名长度必须是2~10位")));

        String bodyTrue = "{\"userId\":11,\"userName\":\"用户名\",\"age\":150,\"strList\":[\"a\",\"b\"],\"validation2Dto\":{\"jobId\":1,\"jobName\":\"工作\"}}";
        reqBuilder = MockMvcRequestBuilders
                .post("/validated/bean-method")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyTrue);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Order(1)
    @Test
    void paramValidate01() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/validated/param-validate?account=&userId=1");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("账号不能为空")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("用户id必须大于等于10")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("账号长度必须是6~20位")));
    }

    @Order(2)
    @Test
    void paramValidate02() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/validated/param-validate?account=账号不够长&userId=10");
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                Matchers.containsString("账号长度必须是6~20位")));
    }

    @Order(3)
    @Test
    void paramValidate03() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/validated/param-validate?account=账号1123232&userId=10");
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Order(4)
    @Test
    void pathValidate() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/validated/path/1");
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().is(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                Matchers.equalTo("id必须大于等于10。")));

        reqBuilder = MockMvcRequestBuilders.get("/validated/path/11");
        super.mockMvc.perform(reqBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }
}