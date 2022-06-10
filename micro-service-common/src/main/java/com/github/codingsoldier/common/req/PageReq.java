package com.github.codingsoldier.common.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author chenpq
 * @since 2022/2/8 14:19
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页码，从 1 开始", example = "1")
    private Integer current = 1;

    @ApiModelProperty(value = "每页多少条", example = "10")
    private Integer size = 10;

}
