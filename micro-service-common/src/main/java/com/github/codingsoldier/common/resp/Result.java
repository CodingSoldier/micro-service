package com.github.codingsoldier.common.resp;

import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回统一的数据结构
 */
@ApiModel(value = "HTTP响应体数据")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "1、code为具体的业务编码，code / 100 = http status \n" +
            "2、code = 20000，http status = 20000/100 = 200 表示请求处理成功 \n" +
            "3、40000 <= code <= 49999 , http status = code/100 表示客户端错误，例如：参数错误 \n" +
            "5、50000 <= code <= 59999 , http status = code/100 表示服务端错误，例如：空指针异常 \n")
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
        return new Result(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
    }

    public static Result fail(String message) {
        return new Result(ResponseCodeEnum.BAD_REQUEST.getCode(), message, null);
    }

    public static Result fail(int code, String message) {
        return new Result(code, message, null);
    }

    public static <T> Result<T> fail(int code, String message, T data) {
        return new Result(code, message, data);
    }

}
