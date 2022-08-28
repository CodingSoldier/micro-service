package com.github.codingsoldier.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OkHttpUtilTest {

    @Test
    void getByte() {
        byte[] imageByte = OkHttpUtil.download("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png");
        assertTrue(imageByte.length > 100);
    }

    @Test
    void getString() {
        String url = "http://localhost:8080/boot-web/http/params/path/111?msg=账号不ddddddddd够长";
        String result = OkHttpUtil.getString(url, null, null);
        assertEquals(true, result.contains("\"code\":0"));
    }

    @Test
    void get() {
        String url = "http://localhost:8080/boot-web/http/params/path/111?msg=账号不ddddddddd够长";
        Result result = OkHttpUtil.get(url, null, null, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());

        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, String> headers = new HashMap<>();
        result = OkHttpUtil.get(url, params, headers, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());

        headers.put("xxxxxx", "aaaaaa");
        result = OkHttpUtil.get("http://localhost:8080/boot-web/http/params/path/111?msg=账号不够长&name=名字",
                params, headers, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());
        assertEquals("111账号不够长名字", result.getData());
    }

    @Test
    void testGet() {
        String url = "http://localhost:8080/boot-web/http/params/path/111";
        HashMap<String, Object> params = new HashMap<>();
        params.put("msg", "账号禅城南村村长");
        params.put("name", "名字");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("xxxxxx", "aaaaaa");
        Result result = OkHttpUtil.get(url, params, headers, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());
        assertEquals("111账号禅城南村村长名字", result.getData());

        Result<String> r2 = OkHttpUtil.get(url, params, headers, new TypeReference<Result<String>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r2.getCode());
        assertEquals("111账号禅城南村村长名字", r2.getData());
    }

    @Test
    void testGet1() {
        String url = "http://localhost:8080/boot-web/http/params/path/111?msg=账号禅城南村村长&name=名字";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("xxxxxx", "aaaaaa");
        Result result = OkHttpUtil.get(url, headers, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());
        assertEquals("111账号禅城南村村长名字", result.getData());

        Result<String> r2 = OkHttpUtil.get(url, headers, new TypeReference<Result<String>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r2.getCode());
        assertEquals("111账号禅城南村村长名字", r2.getData());
    }

    @Test
    void testGet2() {
        String url = "http://localhost:8080/boot-web/http/params/path/111?msg=账号禅城南村村长&name=名字";
        Result result = OkHttpUtil.get(url, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), result.getCode());
        assertEquals("111账号禅城南村村长名字", result.getData());

        Result<String> r2 = OkHttpUtil.get(url, new TypeReference<Result<String>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r2.getCode());
        assertEquals("111账号禅城南村村长名字", r2.getData());
    }

    @Test
    void postString() {
        String url = "http://localhost:8080/boot-web/http/time";
        String result = OkHttpUtil.postString(url, "{}", null);
        assertEquals(true, result.contains("\"code\":0"));
    }

    @Test
    void testPost() {
        String url = "http://localhost:8080/boot-web/http/time";
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", 123456);
        bodyMap.put("localDateTime", 1647187201000L);

        Result r1 = OkHttpUtil.post(url, ObjectMapperUtil.writeValueAsString(bodyMap), null, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r1.getCode());

        Result r2 = OkHttpUtil.post(url, bodyMap, null, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r2.getCode());

        HashMap<String, String> headers = new HashMap<>();
        headers.put("xxxxxx", "val");
        Result<Map<String, Object>> r33 = OkHttpUtil.post(url, bodyMap, headers, new TypeReference<Result<Map<String, Object>>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r33.getCode());
        assertEquals(1647187201000L, r33.getData().get("localDateTime"));

        Result<Map<String, Object>> r44 = OkHttpUtil.post(url, ObjectMapperUtil.writeValueAsString(bodyMap), headers, new TypeReference<Result<Map<String, Object>>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r44.getCode());
        assertEquals(1647187201000L, r44.getData().get("localDateTime"));
    }

    @Test
    void testPost1() {
        String url = "http://localhost:8080/boot-web/http/time";
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", 123456);
        bodyMap.put("localDateTime", 1647187201000L);

        Result r1 = OkHttpUtil.post(url, ObjectMapperUtil.writeValueAsString(bodyMap), Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r1.getCode());

        Result r2 = OkHttpUtil.post(url, bodyMap, Result.class);
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r2.getCode());


        Result<Map<String, Object>> r33 = OkHttpUtil.post(url, bodyMap, new TypeReference<Result<Map<String, Object>>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r33.getCode());
        assertEquals(1647187201000L, r33.getData().get("localDateTime"));

        Result<Map<String, Object>> r44 = OkHttpUtil.post(url, ObjectMapperUtil.writeValueAsString(bodyMap), new TypeReference<Result<Map<String, Object>>>(){});
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r44.getCode());
        assertEquals(1647187201000L, r44.getData().get("localDateTime"));
    }

    @Test
    void testAsynPostMap() throws Exception{
        String url = "http://localhost:8080/boot-web/http/time";
        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("id", 123456);
        bodyMap.put("localDateTime", 1647187201000L);

        OkHttpUtil.asynPost(url, bodyMap, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                assertTrue(false, "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respBodyStr = response.body().string();
                Result r = ObjectMapperUtil.readValue(respBodyStr, Result.class);
                assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r.getCode());
            }
        });

        OkHttpUtil.asynPost(url, bodyMap, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                assertTrue(false, "请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String respBodyStr = response.body().string();
                Result r = ObjectMapperUtil.readValue(respBodyStr, Result.class);
                assertEquals(ResponseCodeEnum.SUCCESS.getCode(), r.getCode());
            }
        });

        TimeUnit.SECONDS.sleep(2L);
    }

}