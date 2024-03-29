package com.github.codingsoldier.example.bootweb.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author cpq
 * @since 2022-03-17 04:10:40
 */
@Data
public class Validation2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "岗位id不能为空。")
    private Long jobId;

    @NotEmpty(message = "岗位名称不能为空。")
    @Length(min = 2, max = 10, message = "岗位名称长度必须是2~10位。")
    private String jobName;

}
