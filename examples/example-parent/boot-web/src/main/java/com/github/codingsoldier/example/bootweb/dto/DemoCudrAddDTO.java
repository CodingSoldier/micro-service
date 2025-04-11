package com.github.codingsoldier.example.bootweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author cpq
 * @since 2025-04-11 23:19:52
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "测试增删改查-新增请求参数", description = "测试增删改查-新增请求参数")
public class DemoCudrAddDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotEmpty(message = "名称不能为空")
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

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "生日")
  private LocalDateTime dateOfBirth;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "幸运日")
  private LocalDate luckDay;

  @Schema(description = "创建人")
  private String createdBy;

  @Schema(description = "更新人")
  private String updatedBy;

  @Schema(description = "更新时间")
  private LocalDateTime createdTime;

  @Schema(description = "更新时间")
  private LocalDateTime updatedTime;

  @Schema(description = "是否删除")
  private Boolean deleted;

}
