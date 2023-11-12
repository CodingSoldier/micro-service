package com.github.codingsoldier.common.resp;

import com.github.codingsoldier.common.enums.ResultCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * ResponseBody返回统一的数据结构
 *
 * @author chenpq05
 * @since 2022/2/23 14:14
 */
@Schema(name = "HTTP响应体数据")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "1、code为具体的业务编码，code = 0 表示成功\n"
            + "2、40000 <= code <= 49999，表示客户端错误，例如：参数错误\n"
            + "3、50000 <= code <= 59999 , 表示服务端错误，例如：空指针异常\n")
    private int code;

    @Schema(description = "提示信息")
    private String message;

    @Schema(description = "返回数据")
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
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
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
