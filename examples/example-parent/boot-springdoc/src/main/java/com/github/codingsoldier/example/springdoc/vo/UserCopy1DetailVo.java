package com.github.codingsoldier.example.springdoc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* @author cpq
* @since 2022-08-04 23:19:00
*/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "用户表copy-详情vo")
public class UserCopy1DetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
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
    private Byte deleted;

}
