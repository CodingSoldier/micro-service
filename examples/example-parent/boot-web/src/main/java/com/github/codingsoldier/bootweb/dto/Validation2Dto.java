package com.github.codingsoldier.bootweb.dto;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cpq
 * @since 2022-03-17 04:10:40
 */
@Data
public class Validation2Dto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "岗位id不能为空。")
    private Long jobId;

    @NotEmpty(message = "岗位名称不能为空。")
    @Length(min = 2, max = 10, message = "岗位名称长度必须是2~10位。")
    private String jobName;

}
