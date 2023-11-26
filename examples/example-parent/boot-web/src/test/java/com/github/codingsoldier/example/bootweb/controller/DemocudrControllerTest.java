package com.github.codingsoldier.example.bootweb.controller;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.github.codingsoldier.example.bootweb.BaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

public class DemocudrControllerTest extends BaseTest {

    @Test
    void add() throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("name", "name"+System.currentTimeMillis());
        body.put("age", new Random().nextInt(100));
        body.put("phone", "18927456512");
        body.put("money", new Random().nextDouble(999));
        body.put("dayMoney", new Random().nextDouble(100));
        body.put("dateOfBirth", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("luckDay", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String bodyJson = ObjectMapperUtil.writeValueAsString(body);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/democudr/add")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyJson);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void addNameEmpty() throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("age", new Random().nextInt(100));
        body.put("phone", "18927456512");
        body.put("money", new Random().nextDouble(999));
        body.put("dayMoney", new Random().nextDouble(100));
        body.put("dateOfBirth", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("luckDay", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String bodyJson = ObjectMapperUtil.writeValueAsString(body);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/democudr/add")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyJson);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("姓名不能为空")));
    }

    @Test
    void update() throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("id", 1);
        body.put("name", "name"+System.currentTimeMillis());
        body.put("age", new Random().nextInt(100));
        body.put("phone", "18927456512");
        body.put("money", new Random().nextDouble(999));
        body.put("dayMoney", new Random().nextDouble(100));
        body.put("dateOfBirth", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("luckDay", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String bodyJson = ObjectMapperUtil.writeValueAsString(body);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .put("/democudr/update")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyJson);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void delete() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .delete("/democudr/delete?id=16")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void detail() throws Exception {
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .get("/democudr/detail?id=1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }

    @Test
    void pageQuery() throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("name", "name");
        String bodyJson = ObjectMapperUtil.writeValueAsString(body);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("/democudr/page")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content(bodyJson);
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResultCodeEnum.SUCCESS.getCode())));
    }
}