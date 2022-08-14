package com.github.codingsoldier.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {

  private OkHttpUtil() {
  }

  public final static String UTF8 = "UTF-8";
  public final static int CONNECT_TIMEOUT = 10;
  public final static int READ_TIMEOUT = 120;
  public final static int WRITE_TIMEOUT = 60;
  public static final MediaType MEDIA_TYPE_JSON = MediaType.parse(
      "application/json; charset=utf-8");
  private static OkHttpClient okHttpClient;

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
   * @param request
   * @return
   */
  @SuppressWarnings("squid:S2589")
  private static String execute(Request request) {
    Response response = null;
    try {
      response = okHttpClient.newCall(request).execute();
      if (response != null) {
        if (!response.isSuccessful()) {
          log.info("http返回code非2XX，code={}", response.code());
        }
        byte[] bytes = response.body().bytes();
        String responseBody = new String(bytes, UTF8);
        log.info("发送HTTP请求，responseBody：{}", responseBody);
        return responseBody;
      }
    } catch (IOException e) {
      log.error("OkHttp异常", e);
      if (response != null) {
        log.error("OkHttp Response", response.toString());
      }
    } finally {
      if (response != null) {
        response.close();
      }
    }
    return null;
  }

  /**
   * 发送get请求，获取byte[]，用于下载
   * @param url
   * @return
   */
  public static byte[] getByte(String url) {
    Request.Builder builder = new Request.Builder();
    Request request = builder.get().url(url).build();
    Response response = null;
    try {
      log.debug("发送HTTP请求，request：{}", request.toString());
      response = okHttpClient.newCall(request).execute();
      byte[] bytes = response.body().bytes();
      return bytes;
    } catch (IOException e) {
      log.error("OkHttp异常", e);
      if (response != null) {
        log.error("OkHttp Response", response.toString());
      }
    } finally {
      if (response != null) {
        response.close();
      }
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
  public static String get(String url, Map<String, Object> params,
      Map<String, String> headers) {
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && headers.size() > 0) {
      headers.forEach((String key, String value) -> builder.addHeader(key, value));
    }
    // 拼接请求参数
    StringBuilder sb = new StringBuilder(url);
    if (params != null && params.keySet().size() > 0) {
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
    log.info("发送HTTP请求，url：{}", request.url());
    String data = execute(request);
    return data;
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
    String data = get(url, params, headers);
    return ObjectMapperUtil.readValue(data, clazz);
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
    String data = get(url, params, headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
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
  public static <T> T get(String url, Map<String, Object> params, Class<T> clazz) {
    String data = get(url, params, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, clazz);
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
  public static <T> T get(String url, Map<String, Object> params, TypeReference<T> valueTypeRef) {
    String data = get(url, params, new HashMap<>(16));
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
    String data = get(url, new HashMap<>(16), new HashMap<>(16));
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
    String data = get(url, new HashMap<>(16), new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

  /**
   * post请求
   *
   * @param url      url
   * @param jsonBody 请求body
   * @param headers  请求头
   * @return 返回clazz类型实体
   */
  public static String post(String url, String jsonBody, Map<String, String> headers) {
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && headers.size() > 0) {
      headers.forEach((String key, String value) -> builder.addHeader(key, value));
    }
    // 创建body
    RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonBody);
    // 构造Request
    Request request = builder.post(requestBody).url(url).build();
    // 执行请求
    log.info("发送HTTP请求，url：{}，body: {}", request.url(), jsonBody);
    String data = execute(request);
    return data;
  }

  /**
   * post请求
   *
   * @param url     url
   * @param mapBody 请求body
   * @param headers 请求头
   * @return 返回clazz类型实体
   */
  public static String post(String url, Map<String, Object> mapBody,
      Map<String, String> headers) {
    String jsonBody = ObjectMapperUtil.writeValueAsString(mapBody);
    String data = post(url, jsonBody, headers);
    return data;
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
    String data = post(url, jsonBody, headers);
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
    String data = post(url, mapBody, headers);
    return ObjectMapperUtil.readValue(data, clazz);
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
    String data = post(url, mapBody, headers);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
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
    String data = post(url, jsonBody, headers);
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
    String data = post(url, jsonBody, new HashMap<>(16));
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
    String data = post(url, jsonBody, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, valueTypeRef);
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
    String data = post(url, jsonBody, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, clazz);
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
    String data = post(url, jsonBody, new HashMap<>(16));
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }




}
