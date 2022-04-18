package com.github.codingsoldier.bootweb.dto;

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
@ApiModel(value = "用户新增")
public class UserAddDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名", example = "姓名01", required = true)
    private String name;

    @ApiModelProperty(value = "年龄", example = "10")
    private Integer age;

    @ApiModelProperty(value = "电话", example = "18952145124")
    private String phone;

    @ApiModelProperty(value = "生日", dataType="long", example = "1647445294184")
    private LocalDateTime dateOfBirth;

}
