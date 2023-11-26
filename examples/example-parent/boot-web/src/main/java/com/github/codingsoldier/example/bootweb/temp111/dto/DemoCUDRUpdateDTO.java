package com.github.codingsoldier.example.bootweb.temp111.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serial;

/**
* @author cpq
* @since 2023-11-26 22:03:44
*/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "demoCUDR表-修改请求参数", description = "demoCUDR表-修改请求参数")
public class DemoCUDRUpdateDTO implements Serializable {

    @Serial
	private static final long serialVersionUID = 1L;

    @Schema(description = "id")
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
    private Long createdBy;

    @Schema(description = "更新人id")
    private Long updatedBy;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "是否已删除，0-未删除，1-删除")
    private Byte deleted;

}
