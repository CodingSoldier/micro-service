package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${packageDto}.${addDtoClassName};
import ${packageDto}.${updateDtoClassName};
import ${packageDto}.${pageQueryDtoClassName};
import ${packageVo}.${detailVoClassName};
import ${packageVo}.${pageVoClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Tag(name = "${table.comment!}-API")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    <#assign uncapFirstServerName="${table.serviceName}"? uncap_first/>

    @Autowired
    private ${table.serviceName} ${uncapFirstServerName};

    @PostMapping("/add")
    @Operation(summary =  "新增")
    public Long add(@RequestBody @Valid ${addDtoClassName} addDto) {
        return ${uncapFirstServerName}.add(addDto);
    }

    @PutMapping("/update")
    @Operation(summary = "修改")
    public void update(@RequestBody @Valid ${updateDtoClassName} updateDto) {
        ${uncapFirstServerName}.update(updateDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public boolean delete(@RequestParam("id") Long id) {
        return ${uncapFirstServerName}.delete(id);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public ${detailVoClassName} detail(@RequestParam("id") Long id) {
        return ${uncapFirstServerName}.detail(id);
    }

    @GetMapping("/page")
    @Operation(summary = "分页")
    public PageResult<${pageVoClassName}> pageQuery(@ModelAttribute @ParameterObject ${pageQueryDtoClassName} queryDto) {
        return ${uncapFirstServerName}.pageQuery(queryDto);
    }

}
</#if>
