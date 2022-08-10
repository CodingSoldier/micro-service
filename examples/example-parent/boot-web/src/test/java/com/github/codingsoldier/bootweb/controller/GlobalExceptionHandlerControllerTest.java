package com.github.codingsoldier.bootweb.controller;

import com.github.codingsoldier.bootweb.BaseTest;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chenpq05
 * @since 2022/8/10 10:07
 */
class GlobalExceptionHandlerControllerTest extends BaseTest {

    @Test
    void testAppException() throws Exception{
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception?id=1&name=AppException");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.equalTo("测试APP异常")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResponseCodeEnum.BAD_REQUEST.getCode())));
    }

    @Test
    void methodArgument() throws Exception{

        // 测试 @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class})
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception?id=sdfsd&name=名字");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.equalTo("参数类型错误。")));

        // 测试 @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        reqBuilder = MockMvcRequestBuilders
                .post("/global/exception/handler/test/more/exception?id=1&name=名字")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .content("{}");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.equalTo("请求方法错误。")));

        // 测试 @ExceptionHandler(MissingServletRequestParameterException.class)
        reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("缺少请求参数")));

        // 测试 @ExceptionHandler(NoHandlerFoundException.class)
        // reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/method/404");
        // super.mockMvc.perform(reqBuilder)
        //         .andExpect(MockMvcResultMatchers.status().isOk())
        //         .andExpect(MockMvcResultMatchers.jsonPath("$.message",
        //                 Matchers.containsString("缺少请求参数")));

        // 测试 @ExceptionHandler(value = IOException.class)
        reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception?id=1&name=IOException");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("IO异常")));

        // 测试 @ExceptionHandler(value = NullPointerException.class)
        reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception?id=1&name=NullPointerException");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",
                        Matchers.containsString("空指针异常")));
    }

    @Test
    void testException() throws Exception{
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.get("/global/exception/handler/test/more/exception?id=1&name=Exception");
        super.mockMvc.perform(reqBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code",
                        Matchers.equalTo(ResponseCodeEnum.SERVER_ERROR.getCode())));
    }
}