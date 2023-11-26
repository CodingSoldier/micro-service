package com.github.codingsoldier.example.bootweb.temp111.dto;

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
 * @since 2023-11-26 22:22:24
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "demoCUDR表-分页查询参数", description = "demoCUDR表-分页查询参数")
public class DemoCUDRPageQueryDTO extends PageReq {

    @Parameter(description = "姓名")
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

    @Parameter(description = "创建人id")
    private Long createdBy;

    @Parameter(description = "更新人id")
    private Long updatedBy;

    @Parameter(description = "创建时间")
    private LocalDateTime createdTime;

    @Parameter(description = "更新时间")
    private LocalDateTime updatedTime;

    @Parameter(description = "是否已删除，0-未删除，1-删除")
    private Byte deleted;

}
