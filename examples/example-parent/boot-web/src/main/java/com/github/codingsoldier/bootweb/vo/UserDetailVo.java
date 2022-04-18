package com.github.codingsoldier.bootweb.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@ApiModel(value = "用户详情")
public class UserDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("生日")
    private LocalDateTime dateOfBirth;

    @ApiModelProperty("创建人id")
    private Long createId;

    @ApiModelProperty("更新人id")
    private Long updateId;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
