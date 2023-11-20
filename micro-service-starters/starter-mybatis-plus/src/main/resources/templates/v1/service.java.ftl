package ${package.Service};

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import ${superServiceClassPackage};
import ${package.Entity}.${entity};
import ${packageDTO}.${addDTOClassName};
import ${packageDTO}.${updateDTOClassName};
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageVO}.${detailVOClassName};
import ${packageVO}.${pageVOClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
     * 新增
     * @param addDTO 新增参数
     * @return id
     */
    Long add(${addDTOClassName} addDTO);

    /**
     * 修改
     * @param updateDTO 修改参数
     * @return id
     */
    void update(${updateDTOClassName} updateDTO);

    /**
     * 删除
     * @param id id
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    ${detailVOClassName} detail(Long id);

    /**
     * 分页
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    PageResult<${pageVOClassName}> pageQuery(${pageQueryDTOClassName} queryDTO);

    /**
     * 是否重复
     * @param id id
     * @param func 列函数
     * @param value 列值
     * @return 是否重复
     */
    boolean isRepeat(Long id, SFunction<${entity},?> func, String value);

}
</#if>
