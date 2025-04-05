package ${package.Mapper};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${customParam.packageDTO}.${customParam.pageQueryDTOClassName};
import ${customParam.packageVO}.${customParam.pageVOClassName};
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
   * @param pageQueryDTO 查询参数
   * @return 分页数据
   */
  Page<${customParam.pageVOClassName}> pageQuery(IPage<?> page, @Param("pageQueryDTO") ${customParam.pageQueryDTOClassName} pageQueryDTO);

}
</#if>
