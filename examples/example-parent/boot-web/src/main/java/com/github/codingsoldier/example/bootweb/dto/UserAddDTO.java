package com.github.codingsoldier.example.bootweb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cpq
 * @since 2022-03-16 21:38:52
 */
@Data
@Schema(name = "用户新增")
public class UserAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "姓名", example = "姓名01", required = true)
    private String name;

    @Schema(description = "年龄", example = "10")
    private Integer age;

    @Schema(description = "电话", example = "18952145124")
    private String phone;

    @Schema(description = "生日", example = "1647445294184")
    private LocalDateTime dateOfBirth;

}
