package ${packageAo};

<#list table.importPackages as pkg>
    <#if pkg ? starts_with("com.baomidou.mybatisplus.annotation")>
        <#continue>
    </#if>
    import ${pkg};
</#list>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
* @author ${author}
* @since ${date}
*/
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "${table.comment!}-新增修改ao")
public class ${addUpdateAoClassName} implements Serializable {

private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>

    <#if field.comment!?length gt 0>
        @ApiModelProperty(value = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

}
