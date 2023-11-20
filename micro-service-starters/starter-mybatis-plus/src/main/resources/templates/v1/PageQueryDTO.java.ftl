package ${packageDTO};

import com.github.codingsoldier.common.req.PageReq;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
<#list table.importPackages as pkg>
    <#if pkg ? starts_with("com.baomidou.mybatisplus.annotation")>
        <#continue>
    </#if>
    <#if pkg ? starts_with("java.io.Serializable")>
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
@EqualsAndHashCode(callSuper = true)
@Schema(name = "${table.comment!}-分页查询参数")
public class ${pageQueryDTOClassName} extends PageReq {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.propertyName == "id">
        <#continue>
    </#if>

    <#if field.comment!?length gt 0>
    @Parameter(description = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
