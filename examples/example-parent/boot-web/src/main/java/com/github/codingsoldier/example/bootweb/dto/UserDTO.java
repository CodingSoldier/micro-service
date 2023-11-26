package com.github.codingsoldier.example.bootweb.dto;

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cpq
 * @since 2022-03-16 21:38:52
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserDTO extends PageReq {

    @Schema(description = "姓名", example = "姓名01", required = true)
    private String name;

    @Schema(description = "年龄", example = "10")
    private Integer age;

}
