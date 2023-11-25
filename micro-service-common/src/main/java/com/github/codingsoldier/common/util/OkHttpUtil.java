package com.github.codingsoldier.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * http工具类
 *
 * @author chenpq05
 * @since 2022-02-09
 */
@Slf4j
public class OkHttpUtil {

  private OkHttpUtil() {
    //sonar
  }

  public static final String HTTP_POST = "POST";
  public static final String HTTP_PUT = "PUT";

  public static final int CONNECT_TIMEOUT = 10;
  public static final int READ_TIMEOUT = 120;
  public static final int WRITE_TIMEOUT = 60;
  public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
  private static final OkHttpClient okHttpClient;

  static {
    OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    //读取超时
    clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
    //连接超时
    clientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
    //写入超时
    clientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

    okHttpClient = clientBuilder.build();
  }

  /**
   * 执行请求
   *
   * @param request request
   * @return byte[]
   */
  private static byte[] execute(Request request) {
    try (Response response = okHttpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        log.error("http返回code非2XX，code={}", response.code());
      }
      try (ResponseBody body = response.body()){
        if (body == null) {
          return new byte[0];
        } else {
          return body.bytes();
        }
      }
    } catch (IOException e) {
      log.error("OkHttp异常", e);
    }
    return new byte[0];
  }

  /**
   * get请求
   *
   * @param url     url
   * @param params  请求参数
   * @param headers 请求头
   */
  private static byte[] get(String url, Map<String, Object> params,
      Map<String, String> headers) {
    if (StringUtils.isBlank(url)) {
      log.error("OkHttpUtil异常，url不能为空");
      return new byte[0];
    }
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && !headers.isEmpty()) {
      headers.forEach(builder::addHeader);
    }
    // 拼接请求参数
    StringBuilder sb = new StringBuilder(url);
    if (params != null && CollectionUtils.isNotEmpty(params.keySet())) {
      boolean firstFlag = true;
      for (Entry<String, Object> entry : params.entrySet()) {
        if (firstFlag) {
          sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
          firstFlag = false;
        } else {
          sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
      }
    }
    // 构造Request
    Request request = builder.get().url(sb.toString()).build();
    // 执行请求
    log.info("OkHttpUtil发送GET请求，url：{}，header：{}", request.url(), headers);
    return execute(request);
  }

  /**
   * 下载
   * @param url url
   * @return byte[]
   */
  public static byte[] download(String url) {
    return OkHttpUtil.get(url, new HashMap<>(16), new HashMap<>(16));
  }

  /**
   * get请求，返回结果是String
   * @param url url
   * @param params params
   * @param headers headers
   * @return String
   */
  public static String getString(String url, Map<String, Object> params, Map<String, String> headers) {
    byte[] dataByte = get(url, params, headers);
    String respBody = new String(dataByte, StandardCharsets.UTF_8);
    log.info("OkHttpUtil发送GET请求，url={}，HTTP返回结果={}", url, respBody);
    return respBody;
  }

  /**
   * get请求
   *
   * @param url    url
   * @param params 请求参数
   * @param clazz  返回结果类型
   * @param <T>    泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, Map<String, Object> params, Map<String, String> headers,
      Class<T> clazz) {
    String bodyStr = getString(url, params, headers);
    return ObjectMapperUtil.readValue(bodyStr, clazz);
  }

  /**
   * get请求
   *
   * @param url          url
   * @param params       请求参数
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, Map<String, Object> params, Map<String, String> headers,
      TypeReference<T> valueTypeRef) {
    String data = getString(url, params, headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * get请求
   *
   * @param url    url
   * @param headers 请求参数
   * @param clazz  返回结果类型
   * @param <T>    泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, Map<String, String> headers, Class<T> clazz) {
    String data = getString(url, null, headers);
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * get请求
   *
   * @param url          url
   * @param headers       请求参数
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, Map<String, String> headers, TypeReference<T> valueTypeRef) {
    String data = getString(url, null, headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * get请求
   *
   * @param url   url
   * @param clazz 返回结果类型
   * @param <T>   泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, Class<T> clazz) {
    String data = getString(url, null, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * get请求
   *
   * @param url          url
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T get(String url, TypeReference<T> valueTypeRef) {
    String data = getString(url, null, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * post请求，返回String
   *
   * @param url      url
   * @param jsonBody 请求body
   * @param headers  请求头
   * @return 返回clazz类型实体
   */
  public static String postPutString(String postPut, String url, String jsonBody, Map<String, String> headers) {
    if (jsonBody == null || StringUtils.isBlank(url)) {
      log.error("http post/put 请求，body不能为null");
      return "";
    } else if (StringUtils.isBlank(url)) {
      log.error("OkHttpUtil异常，url不能为空");
      return "";
    }
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && !headers.isEmpty()) {
      for (Entry<String, String> entry:headers.entrySet()) {
        if (entry.getKey() != null && entry.getValue() != null) {
          builder.addHeader(entry.getKey(), entry.getValue());
        }
      }
    }
    // 创建body
    RequestBody requestBody = RequestBody.create(jsonBody, MEDIA_TYPE_JSON);
    // 构造Request
    Request request = null;
    if (HTTP_POST.equals(postPut)) {
      request = builder.post(requestBody).url(url).build();
      // 执行请求
      log.info("OkHttpUtil发送发送POST请求，url：{}，header：{}，body: {}", request.url(), headers, jsonBody);
    } else if (HTTP_PUT.equals(postPut)) {
      request = builder.put(requestBody).url(url).build();
      // 执行请求
      log.info("OkHttpUtil发送发送POST请求，url：{}，header：{}，body: {}", request.url(), headers, jsonBody);
    }

    byte[] dataByte = execute(request);
    String respBody = new String(dataByte, StandardCharsets.UTF_8);
    log.info("OkHttpUtil发送发送POST请求，url={}，HTTP返回结果={}", url, respBody);
    return respBody;
  }

  /**
   * post请求
   *
   * @param url      url
   * @param jsonBody 请求body
   * @param clazz    返回结果类型
   * @param <T>      泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, String jsonBody, Map<String, String> headers,
      Class<T> clazz) {
    String data = postPutString(HTTP_POST, url, jsonBody, headers);
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * post请求
   *
   * @param url      url
   * @param mapBody 请求body
   * @param clazz    返回结果类型
   * @param <T>      泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, Map<String, Object> mapBody, Map<String, String> headers,
      Class<T> clazz) {
    String data = postPutString(HTTP_POST, url, ObjectMapperUtil.writeValueAsString(mapBody), headers);
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * post请求
   *
   * @param url          url
   * @param jsonBody     请求body
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, String jsonBody, Map<String, String> headers,
      TypeReference<T> valueTypeRef) {
    String data = postPutString(HTTP_POST, url, jsonBody, headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * post请求
   *
   * @param url           url
   * @param mapBody       请求body
   * @param headers       headers
   * @param valueTypeRef  valueTypeRef
   * @param <T>           泛型方法
   * @return              valueTypeRef泛型实体
   */
  public static <T> T post(String url, Map<String, Object> mapBody, Map<String, String> headers,
      TypeReference<T> valueTypeRef) {
    String data = postPutString(HTTP_POST, url, ObjectMapperUtil.writeValueAsString(mapBody), headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * post请求
   *
   * @param url      url
   * @param jsonBody 请求body
   * @param clazz    返回结果类型
   * @param <T>      泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, String jsonBody, Class<T> clazz) {
    String data = postPutString(HTTP_POST, url, jsonBody, null);
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * post请求
   *
   * @param url     url
   * @param mapBody 请求body
   * @param clazz   返回结果类型
   * @param <T>     泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, Map<String, Object> mapBody, Class<T> clazz) {
    String jsonBody = ObjectMapperUtil.writeValueAsString(mapBody);
    String data = postPutString(HTTP_POST, url, jsonBody, null);
    return ObjectMapperUtil.readValue(data, clazz);
  }

  /**
   * post请求
   *
   * @param url          url
   * @param jsonBody     请求body
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, String jsonBody, TypeReference<T> valueTypeRef) {
    String data = postPutString(HTTP_POST, url, jsonBody, null);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * post请求
   *
   * @param url          url
   * @param mapBody      请求body
   * @param valueTypeRef 返回结果类型
   * @param <T>          泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T post(String url, Map<String, Object> mapBody, TypeReference<T> valueTypeRef) {
    String jsonBody = ObjectMapperUtil.writeValueAsString(mapBody);
    String data = postPutString(HTTP_POST, url, jsonBody, null);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * 异步发送post请求
   * @param url           url
   * @param jsonBody      请求body
   * @param headers       headers
   * @param callback      请求返回结果
   */
  public static void asynchronousPost(String url, String jsonBody, Map<String, String> headers, Callback callback) {
    if (jsonBody == null) {
      log.error("http post 请求，body不能为null");
      return ;
    }
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && !headers.isEmpty()) {
      headers.forEach(builder::addHeader);
    }
    // 创建body
    RequestBody requestBody = RequestBody.create(jsonBody, MEDIA_TYPE_JSON);
    // 构造Request
    Request request = builder.post(requestBody).url(url).build();
    // 执行请求
    log.debug("发送异步POST请求，url：{}，header：{}，body: {}", request.url(), headers, jsonBody);
    okHttpClient.newCall(request).enqueue(callback);
  }

  /**
   * 异步发送post请求
   * @param url           url
   * @param mapBody      请求body
   * @param headers       headers
   * @param callback      请求返回结果
   */
  public static void asynchronousPost(String url, Map<String, Object> mapBody, Map<String, String> headers, Callback callback) {
    String jsonBody = ObjectMapperUtil.writeValueAsString(mapBody);
    asynchronousPost(url, jsonBody, headers, callback);
  }

  /**
   * 异步发送post请求
   * @param url           url
   * @param mapBody      请求body
   * @param callback      请求返回结果
   */
  public static void asynchronousPost(String url, Map<String, Object> mapBody, Callback callback) {
    String jsonBody = ObjectMapperUtil.writeValueAsString(mapBody);
    asynchronousPost(url, jsonBody, null, callback);
  }

  /**
   * post请求
   *
   * @param url      url
   * @param jsonBody 请求body
   * @param clazz    返回结果类型
   * @param <T>      泛型方法
   * @return 返回clazz类型实体
   */
  public static <T> T put(String url, String jsonBody, Map<String, String> headers,
      Class<T> clazz) {
    String data = postPutString(HTTP_PUT, url, jsonBody, headers);
    return ObjectMapperUtil.readValue(data, clazz);
  }

}
