package com.github.codingsoldier.common.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * id请求体
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IdReq implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

}
