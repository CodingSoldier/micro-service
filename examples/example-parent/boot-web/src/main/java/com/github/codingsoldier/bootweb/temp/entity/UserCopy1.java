package com.github.codingsoldier.bootweb.temp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表copy
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 22:39:41
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_copy1")
@Schema(name = "用户表copy-对象", description = "用户表copy")
public class UserCopy1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "生日")
    private LocalDateTime dateOfBirth;

    @Schema(description = "创建人id")
    private Long createId;

    @Schema(description = "更新人id")
    private Long updateId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否已删除，0-未删除，1-删除")
    @TableLogic
    private Byte deleted;


}
