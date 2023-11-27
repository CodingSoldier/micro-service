package com.github.codingsoldier.example.cloudweb01.knife4j;

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "分页01-vo", description = "分页01-vo")
public class Page01DTO extends PageReq {

    @Schema(description = "姓名", example = "姓名01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "年龄", example = "10", required = true)
    private Integer age;

}
