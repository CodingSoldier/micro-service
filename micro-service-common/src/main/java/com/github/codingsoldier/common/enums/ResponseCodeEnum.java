package com.github.codingsoldier.common.enums;

/**
 * 响应code类
 *
 * @author chenpq
 * @since 2022/2/8 14:19
 */
public enum ResponseCodeEnum {

    /**
     * http状态码大全 https://seo.juziseo.com/doc/http_code/
     * <p>
     * 规范：
     * 1、0表示成功，其他表示失败
     * 2、如果失败，code统一为5位，前3位为 HTTP 状态码，后2位表示具体业务状态
     * 3、客户端错误，以4开头。例如：
     * http status = 429 表示“并发请求过多”，则使用 42901 - 42999 表示“并发请求过多” 这类的异常，
     * 例如：使用 42901 表示“系统忙，您发送的请求被限流了。”
     * 4、服务端错误，以5开头
     * http status = 500 表示“服务器端程序错误”，则使用 50001 - 50099 表示“并服务器端程序错误” 这类的异常
     */
    SUCCESS(0, "成功"),
    BAD_REQUEST(40000, "失败"),
    TOO_MANY_REQUESTS(42901, "系统忙，您发送的请求被限流了。"),
    PRECONDITION_FAILED(41200, "请求条件错误"),
    SERVER_ERROR(50000, "服务端无法处理此请求"),
    ;

    private int code;
    private String message;

    ResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
