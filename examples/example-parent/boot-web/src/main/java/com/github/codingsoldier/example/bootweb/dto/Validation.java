package com.github.codingsoldier.example.bootweb.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.List;

/**
 * @author cpq
 * @since 2022-03-17 04:10:40
 */
@Data
public class Validation implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户id不能为空。")
    private Long userId;

    @NotEmpty(message = "用户姓名不能为空")
    @Length(min = 2, max = 10, message = "用户姓名长度必须是2~10位")
    private String userName;

    @Min(value = 10, message = "年龄必须大于10")
    @Max(value = 150, message = "年龄必须小于150")
    private Integer age;

    @Valid // 嵌套验证必须用@Valid
    @NotNull(message = "列表不能为空。")
    @Size(min = 1, message = "列表至少要有一个自定义属性。")
    private List<
                @NotBlank(message = "列表元素不能为空。")
                @Pattern(regexp = "^(a|b|c|){1}$", message = "列表元素不正确（a-原始、b-续签、c-补充）")
                        String> strList;
    @Valid
    private Validation2 validation2Dto;

}
