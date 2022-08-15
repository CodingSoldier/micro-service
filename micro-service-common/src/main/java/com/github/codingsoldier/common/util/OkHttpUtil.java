package com.github.codingsoldier.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.codingsoldier.common.util.objectmapper.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {

  private OkHttpUtil() {
    //sonar
  }

  public static final int CONNECT_TIMEOUT = 10;
  public static final int READ_TIMEOUT = 120;
  public static final int WRITE_TIMEOUT = 60;
  public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
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
  private static byte[] execute(Request request) {
    try (Response response = okHttpClient.newCall(request).execute()){
      if (response != null) {
        if (!response.isSuccessful()) {
          log.info("http返回code非2XX，code={}", response.code());
        }
        return response.body().bytes();
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
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && headers.size() > 0) {
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
    log.debug("发送GET请求，url：{}，header：{}", request.url(), headers);
    return execute(request);
  }

  /**
   * 下载
   * @param url
   * @return
   */
  public static byte[] download(String url) {
    return OkHttpUtil.get(url, new HashMap<>(), new HashMap<>());
  }

  /**
   * get请求，返回结果是String
   * @param url
   * @param params
   * @param headers
   * @return
   */
  public static String getString(String url, Map<String, Object> params, Map<String, String> headers) {
    byte[] dataByte = get(url, params, headers);
    String respBody = new String(dataByte, StandardCharsets.UTF_8);
    log.debug("HTTP返回结果={}", respBody);
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
  public static String postString(String url, String jsonBody, Map<String, String> headers) {
    if (jsonBody == null) {
      log.error("http post 请求，body不能为null");
      return "";
    }
    // 创建builder
    Request.Builder builder = new Request.Builder();
    // 设置请求头
    if (headers != null && headers.size() > 0) {
      headers.forEach(builder::addHeader);
    }
    // 创建body
    RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, jsonBody);
    // 构造Request
    Request request = builder.post(requestBody).url(url).build();
    // 执行请求
    log.debug("发送POST请求，url：{}，header：{}，body: {}", request.url(), headers, jsonBody);
    byte[] dataByte = execute(request);
    String respBody = new String(dataByte, StandardCharsets.UTF_8);
    log.debug("HTTP返回结果={}", respBody);
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
    String data = postString(url, jsonBody, headers);
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
    String data = postString(url, ObjectMapperUtil.writeValueAsString(mapBody), headers);
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
    String data = postString(url, jsonBody, headers);
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
    String data = postString(url, ObjectMapperUtil.writeValueAsString(mapBody), headers);
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
    String data = postString(url, jsonBody, null);
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
    String data = postString(url, jsonBody, null);
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
    String data = postString(url, jsonBody, null);
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
    String data = postString(url, jsonBody, null);
    return ObjectMapperUtil.readValue(data, valueTypeRef);
  }

}
