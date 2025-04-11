package com.github.codingsoldier.example.bootweb.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@Schema(name = "测试增删改查-详情响应数据", description = "测试增删改查-详情响应数据")
public class DemoCudrDetailVO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Schema(description = "主键")
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
