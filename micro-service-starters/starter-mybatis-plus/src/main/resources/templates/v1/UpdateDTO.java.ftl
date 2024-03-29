package ${packageDTO};

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
<#list table.importPackages as pkg>
    <#if pkg ? starts_with("com.baomidou.mybatisplus.annotation")>
        <#continue>
    </#if>
import ${pkg};
</#list>

/**
* @author ${author}
* @since ${date}
*/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "${table.comment!}-修改请求参数", description = "${table.comment!}-修改请求参数")
public class ${updateDTOClassName} implements Serializable {

    @Serial
	private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.comment!?length gt 0>
    @Schema(description = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
