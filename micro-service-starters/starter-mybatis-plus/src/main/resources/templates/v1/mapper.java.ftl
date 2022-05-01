package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import ${packageVo}.${pageVoClassName};
import ${packageDto}.${pageQueryDtoClassName};
import com.baomidou.mybatisplus.core.metadata.IPage;
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
     * @param pageQueryVo 查询参数
     * @return 分页数据
     */
    IPage<${pageVoClassName}> pageQuery(IPage<?> page, @Param("pageQueryVo") ${pageQueryDtoClassName} pageQueryVo);

}
</#if>
