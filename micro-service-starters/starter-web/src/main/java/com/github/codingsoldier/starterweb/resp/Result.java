package com.github.codingsoldier.starterweb.resp;

import com.github.codingsoldier.common.enums.RespCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回统一的数据结构
 */
@ApiModel(value = "HTTP响应体数据")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "code=0处理成功，code>0处理失败，code<0处理请求时发生异常")
    private int code;

    @ApiModelProperty(value = "提示信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result() {
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
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


    /**
     * 自定义方法
     */

    public static <T> Result<T> success() {
        return new Result(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result(RespCodeEnum.SUCCESS.getCode(), RespCodeEnum.SUCCESS.getMessage(), data);
    }

    public static Result fail(String message) {
        return new Result(RespCodeEnum.FAIl.getCode(), message, null);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }

    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result(code, message, data);
    }

}
