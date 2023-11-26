package ${package.Controller};

import com.github.codingsoldier.common.resp.Result;
import ${package.Service}.${table.serviceName};
import ${packageDTO}.${addDTOClassName};
import ${packageDTO}.${updateDTOClassName};
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageVO}.${detailVOClassName};
import ${packageVO}.${pageVOClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
    public Result<?> update(@RequestBody @Valid ${updateDTOClassName} updateDTO) {
        ${uncapFirstServerName}.update(updateDTO);
        return Result.success();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "删除", description = "返回是否成功")
    @Parameters({
        @Parameter(name = "id",description = "id"),
    })
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        boolean delete = ${uncapFirstServerName}.delete(id);
        return Result.success(delete);
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "详情")
    @Parameters({
        @Parameter(name = "id",description = "id"),
    })
    @GetMapping("/detail")
    public Result<${detailVOClassName}> detail(@RequestParam("id") Long id) {
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
