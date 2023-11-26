package ${package.Controller};

import com.github.codingsoldier.common.resp.Result;
import ${packageDTO}.${addDTOClassName};
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageDTO}.${updateDTOClassName};
import ${package.Service}.${table.serviceName};
import ${packageVO}.${detailVOClassName};
import ${packageVO}.${pageVOClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
@ApiSupport(order = 100)
@Tag(name = "${table.comment!}-API")
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    <#assign uncapFirstServerName="${table.serviceName}"? uncap_first/>

    private final ${table.serviceName} ${uncapFirstServerName};

    public ${table.controllerName}(${table.serviceName} ${uncapFirstServerName}) {
        this.${uncapFirstServerName} = ${uncapFirstServerName};
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "新增", description = "返回id")
    @PostMapping("/add")
    public Result<Long> add(@RequestBody @Valid ${addDTOClassName} addDTO) {
        Long id = ${uncapFirstServerName}.add(addDTO);
        return Result.success(id);
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "修改")
    @PutMapping("/update")
    public Result<Void> update(@RequestBody @Valid ${updateDTOClassName} updateDTO) {
        ${uncapFirstServerName}.update(updateDTO);
        return Result.success();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "删除", description = "返回是否成功")
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestParam("id") @Parameter(description = "主键") Long id) {
        boolean delete = ${uncapFirstServerName}.delete(id);
        return Result.success(delete);
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "详情")
    @GetMapping("/detail")
    public Result<${detailVOClassName}> detail(@RequestParam("id") @Parameter(description = "主键") Long id) {
        ${detailVOClassName} detail = ${uncapFirstServerName}.detail(id);
        return Result.success(detail);
    }

    @ApiOperationSupport(order = 50)
    @Operation(summary = "分页")
    @PostMapping("/page")
    public Result<PageData<${pageVOClassName}>> pageQuery(@RequestBody ${pageQueryDTOClassName} queryDTO) {
        PageData<${pageVOClassName}> pageData = ${uncapFirstServerName}.pageQuery(queryDTO);
        return Result.success(pageData);
    }

}
</#if>
