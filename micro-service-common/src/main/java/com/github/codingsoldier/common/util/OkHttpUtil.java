package com.github.codingsoldier.common.util;

import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {

    public final static String UTF8 = "UTF-8";
    public final static int READ_TIMEOUT = 100;
    public final static int CONNECT_TIMEOUT = 60;
    public final static int WRITE_TIMEOUT = 60;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient;

    static  {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //读取超时
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        //连接超时
        clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        //写入超时
        clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        //支持HTTPS请求，跳过证书验证
        // clientBuilder.hostnameVerifier((hostname, session) -> true);

        okHttpClient = clientBuilder.build();
    }

    /**
     * 执行请求
     * @param request
     * @return
     */
    private static String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            byte[] bytes = response.body().bytes();
            return new String(bytes, UTF8);
        }catch (IOException e){
            log.error("OkHttp异常", e);
            if (response != null){
                log.error("OkHttp Response", response.toString());
            }
        }finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * get请求
     *
     * @param url url
     * @param params 请求参数
     * @param headers 请求头
     * @param clazz 返回结果类型
     * @param <T> 泛型
     * @param <V> 泛型
     * @return 返回clazz类型实体
     */
    public static <T, V> T get(String url, Map<String, V> params, Map<String, String> headers, Class<T> clazz) {
        // 创建builder
        Request.Builder builder = new Request.Builder();
        // 设置请求头
        if (headers != null && headers.size() > 0){
            headers.forEach((String key, String value) -> builder.header(key, value));
        }
        // 拼接请求参数
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }
        // 构造Request
        Request request = builder.get().url(url).build();
        // 执行请求
        String data = execute(request);
        if (StringUtils.isEmpty(data)){
            return null;
        }
        // 返回结果
        return ObjectMapperUtil.readValue(data, clazz);
    }

    /**
     * get请求
     *
     * @param url url
     * @param params 请求参数
     * @param clazz 返回结果类型
     * @param <T> 泛型
     * @param <V> 泛型
     * @return 返回clazz类型实体
     */
    public static <T,V> T get(String url, Map<String, V> params, Class<T> clazz) {
        return get(url, params, null, clazz);
    }

    /**
     * get请求
     *
     * @param url url
     * @param clazz 返回结果类型
     * @param <T> 泛型方法
     * @return 返回clazz类型实体
     */
    public static <T> T get(String url, Class<T> clazz) {
        return get(url, null, null, clazz);
    }

    /**
     * post请求
     *
     * @param url url
     * @param jsonBody 请求body
     * @param headers 请求头
     * @param clazz 返回结果类型
     * @param <T> 泛型方法
     * @return 返回clazz类型实体
     */
    public static <T> T post(String url, String jsonBody, Map<String, String> headers, Class<T> clazz) {
        // 创建builder
        Request.Builder builder = new Request.Builder();
        // 设置请求头
        if (headers != null && headers.size() > 0){
            headers.forEach((String key, String value) -> builder.header(key, value));
        }
        // 创建body
        RequestBody requestBody = RequestBody.create(JSON, jsonBody);
        // 构造Request
        Request request = builder.post(requestBody).url(url).build();
        // 执行请求
        String data = execute(request);
        if (StringUtils.isEmpty(data)){
            return null;
        }
        // 返回结果
        return ObjectMapperUtil.readValue(data, clazz);
    }

    /**
     * post请求
     *
     * @param url url
     * @param jsonBody 请求body
     * @param clazz 返回结果类型
     * @param <T> 泛型方法
     * @return 返回clazz类型实体
     */
    public static <T> T post(String url, String jsonBody, Class<T> clazz) {
        return post(url, jsonBody, null, clazz);
    }

    /**
     * post请求
     *
     * @param url url
     * @param body 请求body
     * @param clazz 返回结果类型
     * @param <T> 泛型
     * @param <V> 泛型
     * @return 返回clazz类型实体
     */
    public static <T, V> T post(String url, Map<String, V> body, Class<T> clazz) {
        String jsonBody = ObjectMapperUtil.writeValueAsString(body);
        return post(url, jsonBody, null, clazz);
    }

    public static void main(String[] args) {
        Map result = get("http://localhost:8080/example/validations/account?userId=1&account", Map.class);
        System.out.println(result);

        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", 1);
        params.put("account", "账号");
        result = get("http://localhost:8080/example/validations/account", params, Map.class);
        System.out.println(result);

        HashMap<String, Object> body = new HashMap<>();
        HashMap<String, Object> vo = new HashMap<>();
        body.put("userId", 1);
        body.put("userName", "sdsdff");
        body.put("strList", Lists.newArrayList("a"));
        body.put("validation2Vo", vo);
        vo.put("jobId", 111);
        vo.put("jobName", "234324");
        result = post("http://localhost:8080/example/validations/add", body, Map.class);
        System.out.println(result);

    }


}
