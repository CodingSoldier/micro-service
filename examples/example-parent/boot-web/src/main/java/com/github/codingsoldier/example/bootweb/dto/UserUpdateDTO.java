package com.github.codingsoldier.example.bootweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author cpq
 * @since 2022-03-16 21:38:52
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "用户更新请求数据", description = "用户更新请求数据")
public class UserUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "姓名", example = "姓名01", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "年龄", example = "10")
    private Integer age;

    @Schema(description = "电话", example = "18952145124")
    private String phone;

    @Schema(description = "生日", example = "2022-01-01 01:02:02")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime dateOfBirth;

    @Schema(description = "内部实体2")
    private UserUpdate2DTO userUpdate2;

    @Schema(description = "内部实体列表")
    private List<UserUpdate2DTO> list;

}
