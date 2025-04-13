package com.github.codingsoldier.common.resp;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;

/**
 * ResponseBody返回统一的数据结构
 *
 * @author chenpq05
 * @since 2022/2/23 14:14
 */
public class Result<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = """
     <br /> 规范：
     <br /> 1、code统一为5位。
     <br /> 2、20000 <= code <= 29999 表示成功，其他表示失败。
     <br /> 3、客户端错误，以4开头。例如：
     <br />   http status = 429 表示“并发请求过多”，则使用 42900 - 42999 表示“并发请求过多” 这类的异常，
     <br />   例如：使用 42901 表示“系统忙，您发送的请求被限流了”。
     <br /> 4、服务端错误，以5开头。
     <br />   http status = 500 表示“服务器端程序错误”，则使用 50000 - 50099 表示“并服务器端程序错误” 这类的异常。
     <br /> 5、后端约定http status = 200只表明服务端能就收到请求并返回结果，并不意味着服务端能正确处理请求。
     <br />   当http status = 200，code的取值范围是大于等于20000，小于等于59999，
     <br />   所以当http status = 200时，也要判断code的值。
     <br /> 6、建议：前端收到响应，40000 <= code <= 49999，使用黄色弹出框；50000 <= code <= 59999，使用红色弹出框。
     """, example = "20000")
  private int code;

  @Schema(description = "提示信息")
  private String message;

  @Schema(description = "数据")
  @SuppressWarnings("squid:S1948")
  private T data;

  public Result() {
  }

  public Result(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> Result<T> success() {
    return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(),
        null);
  }

  public static <T> Result<T> success(T data) {
    return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(),
        data);
  }

  public static <T> Result<T> fail(String message) {
    return new Result<>(ResultCodeEnum.BAD_REQUEST.getCode(), message, null);
  }

  public static <T> Result<T> fail(int code, String message) {
    return new Result<>(code, message, null);
  }

  public static <T> Result<T> fail(int code, String message, T data) {
    return new Result<>(code, message, data);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Result{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }
}
