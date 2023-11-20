package ${package.Controller};

import com.github.codingsoldier.common.resp.Result;
import ${package.Service}.${table.serviceName};
import ${packageDTO}.${addDTOClassName};
import ${packageDTO}.${updateDTOClassName};
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageVO}.${detailVOClassName};
import ${packageVO}.${pageVOClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public Result<Long> add(@RequestBody @Valid ${addDTOClassName} addDTO) {
        Long id = ${uncapFirstServerName}.add(addDTO);
        return Result.success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "修改")
    public Result<?> update(@RequestBody @Valid ${updateDTOClassName} updateDTO) {
        ${uncapFirstServerName}.update(updateDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除")
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        boolean delete = ${uncapFirstServerName}.delete(id);
        return Result.success(delete);
    }

    @GetMapping("/detail")
    @Operation(summary = "详情")
    public Result<${detailVOClassName}> detail(@RequestParam("id") Long id) {
        ${detailVOClassName} detail = ${uncapFirstServerName}.detail(id);
        return Result.success(detail);
    }

    @PostMapping("/page")
    @Operation(summary = "分页")
    public PageResult<${pageVOClassName}> pageQuery(@RequestBody ${pageQueryDTOClassName} queryDTO) {
        return ${uncapFirstServerName}.pageQuery(queryDTO);
    }

}
</#if>
