package com.github.codingsoldier.bootweb.dto;

import com.github.codingsoldier.starter.mybatisplus.req.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户分页vo")
public class PageDto extends PageReq {

    @ApiModelProperty(value = "姓名", example = "姓名01")
    private String name;

    @ApiModelProperty(value = "年龄", example = "10")
    private Integer age;

}
