package com.github.codingsoldier.common.enums;

/**
 * 响应code类
 * @since 2022/2/8 14:19
 * @author chenpq
 */
public enum RespCodeEnum {

    /**
     * http状态码大全 https://seo.juziseo.com/doc/http_code/
     */
    SUCCESS(0, "成功"),
    FAIl(1, "失败"),
    ERROR(-1, "发生错误"),
    // 412 请求条件错误
    PRECONDITION_FAILED(41200, "请求条件错误"),
    ;

    private int code;
    private String message;

    RespCodeEnum(int code, String message) {
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
