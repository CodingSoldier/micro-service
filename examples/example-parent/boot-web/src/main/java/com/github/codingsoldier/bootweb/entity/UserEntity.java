package com.github.codingsoldier.bootweb.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2022-03-17 01:28:55
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
@ApiModel(value = "UserEntity对象", description = "用户表")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty("更新人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已删除，0-未删除，1-删除")
    @TableLogic
    private Integer deleted;


}
