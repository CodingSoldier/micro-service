package com.github.codingsoldier.example.cloudweb01.controller.testswagger;

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "分页01-vo")
public class Page01Dto extends PageReq {

    @Parameter(description = "姓名", example = "姓名01")
    private String name;

    @Parameter(description = "年龄", example = "10")
    private Integer age;

}
