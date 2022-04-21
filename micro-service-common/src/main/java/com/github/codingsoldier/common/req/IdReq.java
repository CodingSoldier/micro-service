package com.github.codingsoldier.common.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class IdReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true)
    private Long id;

    public IdReq() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
