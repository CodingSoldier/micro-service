package com.github.codingsoldier.example.bootweb.dto;

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
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
@EqualsAndHashCode(callSuper = true)
@Schema(name = "测试增删改查-分页查询参数", description = "测试增删改查-分页查询参数")
public class DemoCudrPageQueryDTO extends PageReq {

  @Parameter(description = "名称")
  private String name;

  @Parameter(description = "年龄")
  private Integer age;

  @Parameter(description = "电话")
  private String phone;

  @Parameter(description = "钱")
  private Double money;

  @Parameter(description = "日赚")
  private Double dayMoney;

  @Parameter(description = "生日")
  private LocalDateTime dateOfBirth;

  @Parameter(description = "幸运日")
  private LocalDate luckDay;

  @Parameter(description = "创建人")
  private String createdBy;

  @Parameter(description = "更新人")
  private String updatedBy;

  @Parameter(description = "更新时间")
  private LocalDateTime createdTime;

  @Parameter(description = "更新时间")
  private LocalDateTime updatedTime;

  @Parameter(description = "是否删除")
  private Boolean deleted;

}
