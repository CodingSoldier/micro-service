package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import ${packageDTO}.${addDTOClassName};
import ${packageDTO}.${pageQueryDTOClassName};
import ${packageDTO}.${updateDTOClassName};
import ${packageVO}.${detailVOClassName};
import ${packageVO}.${pageVOClassName};
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public Long add(${addDTOClassName} addDTO) {
        //if (isRepeat(null, ${entity}::getName, addDTO.getName())){
        //    throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "新增失败，XX已存在。请修改XX。");
        //}
        ${entity} ${uncapFirstEntity} = new ${entity}();
        BeanUtils.copyProperties(addDTO, ${uncapFirstEntity});
        super.save(${uncapFirstEntity});
        return ${uncapFirstEntity}.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${updateDTOClassName} updateDTO) {
        //if (isRepeat(updateDTO.getId(), ${entity}::getName, updateDTO.getName())){
        //    throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
        //}
        ${entity} ${uncapFirstEntity} = new ${entity}();
        BeanUtils.copyProperties(updateDTO, ${uncapFirstEntity});
        super.updateById(${uncapFirstEntity});
    }
 
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return super.removeById(id);
    }

    @Override
    public ${detailVOClassName} detail(Long id) {
        ${entity} ${uncapFirstEntity} = super.getById(id);
        ${detailVOClassName} detailVO = new ${detailVOClassName}();
        BeanUtils.copyProperties(${uncapFirstEntity}, detailVO);
        return detailVO;
    }

    @Override
    public PageData<${pageVOClassName}> pageQuery(${pageQueryDTOClassName} queryDTO) {
        Page<${pageVOClassName}> p = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<${pageVOClassName}> pageData = ${uncapFirstMapper}.pageQuery(p, queryDTO);
        List<${pageVOClassName}> listData = pageData.getRecords();
        return PageData.create(pageData, listData);
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
