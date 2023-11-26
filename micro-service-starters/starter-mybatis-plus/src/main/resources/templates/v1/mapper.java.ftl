package ${package.Mapper};

import com.baomidou.mybatisplus.core.metadata.IPage;
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageVO}.${pageVOClassName};
import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotation>
import org.apache.ibatis.annotations.Mapper;
</#if>
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    /**
     * 分页
     * @param page page
     * @param pageQueryVO 查询参数
     * @return 分页数据
     */
    IPage<${pageVOClassName}> pageQuery(IPage<?> page, @Param("pageQueryVO") ${pageQueryDTOClassName} pageQueryVO);

}
</#if>
