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
 * @since 2023-11-26 22:32:54
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "demoCUDR表-新增请求参数", description = "demoCUDR表-新增请求参数")
public class DemoCUDRAddDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "姓名不能为空")
    @Schema(description = "姓名", example = "姓名01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "钱")
    private Double money;

    @Schema(description = "日赚")
    private Double dayMoney;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "生日")
    private LocalDateTime dateOfBirth;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @Schema(description = "幸运日")
    private LocalDate luckDay;


}
