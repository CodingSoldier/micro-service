package com.github.codingsoldier.starter.mybatisplus.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @since 2022/2/8 14:19
 * @author chenpq
 */

public class PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码，从 1 开始", example = "1")
    private Integer current = 1;

    @ApiModelProperty(value = "每页多少条", example = "10")
    private Integer size = 10;

    public PageReq() {
    }

    public PageReq(Integer current, Integer size) {
        this.current = current;
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
