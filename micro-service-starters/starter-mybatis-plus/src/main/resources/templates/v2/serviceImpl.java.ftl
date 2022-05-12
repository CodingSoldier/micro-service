package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${packageAo}.${addUpdateAoClassName};
import ${packageDto}.${pageQueryDtoClassName};
import ${packageVo}.${detailVoClassName};
import ${packageVo}.${pageVoClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
	<#assign uncapFirstEntity="${entity}"? uncap_first/>
	<#assign uncapFirstMapper="${table.mapperName}"? uncap_first/>

    @Autowired
    private ${table.mapperName} ${uncapFirstMapper};

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addUpdate(${addUpdateAoClassName} addUpdateAo) {
        // id 为空，新增；id 不为空，修改
        Long id = addUpdateAo.getId();
        // if (isRepeat(updateDto.getId(), ${entity}::getName, updateDto.getName())){
        //     throw new AppException(ResponseCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
        // }
        ${entity} ${uncapFirstEntity} = new ${entity}();
        BeanUtils.copyProperties(addUpdateAo, ${uncapFirstEntity});
        if (Objects.isNull(id)){
            // 新增
            super.save(${uncapFirstEntity});
            id = ${uncapFirstEntity}.getId();
        } else {
            // 修改
            super.updateById(${uncapFirstEntity});
        }
        return id;
    }
 
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // 删除
        boolean r = super.removeById(id);
        return r;
    }

    @Override
    public ${detailVoClassName} detail(Long id) {
        ${entity} ${uncapFirstEntity} = super.getById(id);
        ${detailVoClassName} detailVo = new ${detailVoClassName}();
        BeanUtils.copyProperties(${uncapFirstEntity}, detailVo);
        return detailVo;
    }

    @Override
    public PageResult<${pageVoClassName}> pageQuery(${pageQueryDtoClassName} queryDto) {
        Page<${pageVoClassName}> p = new Page<>(queryDto.getCurrent(), queryDto.getSize());
        IPage<${pageVoClassName}> pageData = ${uncapFirstMapper}.pageQuery(p, queryDto);
        List<${pageVoClassName}> listData = pageData.getRecords();
        return PageResult.create(pageData, listData);
    }

    @Override
    public boolean isRepeat(Long id, SFunction<${entity},?> func, String value) {
        LambdaQueryWrapper<${entity}> lqw = Wrappers.lambdaQuery();
        lqw.eq(${entity}::getDeleted, 0);
        lqw.eq(func, value);
        if (Objects.nonNull(id)) {
            lqw.ne(${entity}::getId, id);
        }
        return super.count(lqw) > 0;
    }

}
</#if>
