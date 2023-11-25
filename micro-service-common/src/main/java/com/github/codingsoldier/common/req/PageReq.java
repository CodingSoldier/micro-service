package com.github.codingsoldier.common.req;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 分页基础请求体
 * @author chenpq
 * @since 2022/2/8 14:19
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PageReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Parameter(description = "页码，从 1 开始", example = "1")
    private Integer current = 1;

    @Parameter(description = "每页多少条", example = "10")
    private Integer size = 10;

}
