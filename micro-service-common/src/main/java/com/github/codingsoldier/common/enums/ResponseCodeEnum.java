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
     * 约定：
     * 1、code为具体的业务编码，code / 100 = http status
     * 2、code = 20000，http status = 20000/100 = 200 表示请求处理成功
     * 3、40000 <= code <= 49999 , http status = code/100 表示客户端错误，例如：参数错误
     * 5、50000 <= code <= 59999 , http status = code/100 表示服务端错误，例如：空指针异常
     */
    SUCCESS(20000, "成功"),
    BAD_REQUEST(40000, "失败"),
    TOO_MANY_REQUESTS(42901, "系统忙，您发送的请求被限流了。"),
    SERVER_ERROR(50000, "发生错误"),
    PRECONDITION_FAILED(41200, "请求条件错误"),
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
