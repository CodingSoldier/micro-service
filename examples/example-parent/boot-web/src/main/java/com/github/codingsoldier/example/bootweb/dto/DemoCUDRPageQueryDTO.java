package com.github.codingsoldier.example.bootweb.dto;

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author cpq
 * @since 2023-11-26 22:32:54
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

}
