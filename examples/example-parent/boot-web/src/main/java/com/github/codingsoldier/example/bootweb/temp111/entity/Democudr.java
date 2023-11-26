package com.github.codingsoldier.example.bootweb.temp111.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serial;

/**
 * <p>
 * demoCUDR表
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 22:03:44
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "demoCUDR表", description = "demoCUDR表")
public class Democudr implements Serializable {

    @Serial
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

    @Schema(description = "钱")
    private Double money;

    @Schema(description = "日赚")
    private Double dayMoney;

    @Schema(description = "生日")
    private LocalDateTime dateOfBirth;

    @Schema(description = "幸运日")
    private LocalDate luckDay;

    @Schema(description = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "更新人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @Schema(description = "是否已删除，0-未删除，1-删除")
    @TableLogic
    private Byte deleted;


}
