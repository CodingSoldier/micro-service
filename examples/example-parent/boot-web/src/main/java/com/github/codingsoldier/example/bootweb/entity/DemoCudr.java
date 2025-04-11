package com.github.codingsoldier.example.bootweb.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 测试增删改查
 *
 * @author cpq
 * @since 2025-04-11 23:19:52
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName("demo_cudr")
@Schema(name = "测试增删改查", description = "测试增删改查")
public class DemoCudr implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  @Schema(description = "名称")
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

  @Schema(description = "创建人")
  private String createdBy;

  @Schema(description = "更新人")
  private String updatedBy;

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createdTime;

  @Schema(description = "更新时间")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime updatedTime;

  @Schema(description = "是否删除")
  @TableLogic
  private Boolean deleted;


}
