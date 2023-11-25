package com.github.codingsoldier.common.enums;

/**
 * 响应code类
 *
 * @author chenpq
 * @since 2022/2/8 14:19
 */
public enum ResultCodeEnum {

    /**
     规范：
     1、code统一为5位，前3位为 HTTP 状态码，后2位表示具体业务状态
     2、20000 <= code <= 29999 表示成功，其他表示失败
     3、客户端错误，以 4 开头。例如：
     http status = 429 表示“并发请求过多”，则使用 42900 - 42999 表示“并发请求过多” 这类的异常，
     例如：使用 42901 表示“系统忙，您发送的请求被限流了。”
     4、服务端错误，以 5 开头
     http status = 500 表示“服务器端程序错误”，则使用 50000 - 50099 表示“并服务器端程序错误” 这类的异常
     5、建议：前端收到响应，40000 <= code <= 49999，使用黄色弹出框。50000 <= code <= 59999，使用红色弹出框。
     */
    SUCCESS(20000, "成功"),
    BAD_REQUEST(40000, "失败"),
    PRECONDITION_FAILED(40001, "请求条件错误"),
    TOO_MANY_REQUESTS(42901, "系统忙，您发送的请求被限流了。"),
    FEIGN_RESULT_ERROR_EX(45000, "微服务之间调用异常"),
    SERVER_ERROR(50000, "服务端无法处理此请求"),
    BACKEND_SERVER_ERROR(50010, "服务端处理异常"),
    ;

    private final int code;
    private final String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
