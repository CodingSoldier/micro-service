package com.github.codingsoldier.example.bootweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import com.github.codingsoldier.example.bootweb.entity.Democudr;
import com.github.codingsoldier.example.bootweb.mapper.DemocudrMapper;
import com.github.codingsoldier.example.bootweb.service.DemocudrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingsoldier.example.bootweb.dto.DemoCUDRAddDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCUDRPageQueryDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCUDRUpdateDTO;
import com.github.codingsoldier.example.bootweb.vo.DemoCUDRDetailVO;
import com.github.codingsoldier.example.bootweb.vo.DemoCUDRPageVO;
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
 * demoCUDR表 服务实现类
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 22:32:54
 */
@Slf4j
@Service
public class DemocudrServiceImpl extends ServiceImpl<DemocudrMapper, Democudr> implements DemocudrService {

    @Autowired
    private DemocudrMapper democudrMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(DemoCUDRAddDTO addDTO) {
        if (isRepeat(null, Democudr::getName, addDTO.getName())){
           throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "新增失败，名称已存在。请修改名称。");
        }
        Democudr democudr = new Democudr();
        BeanUtils.copyProperties(addDTO, democudr);
        super.save(democudr);
        return democudr.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DemoCUDRUpdateDTO updateDTO) {
        if (isRepeat(updateDTO.getId(), Democudr::getName, updateDTO.getName())){
           throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
        }
        Democudr democudr = new Democudr();
        BeanUtils.copyProperties(updateDTO, democudr);
        super.updateById(democudr);
    }
 
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return super.removeById(id);
    }

    @Override
    public DemoCUDRDetailVO detail(Long id) {
        Democudr democudr = super.getById(id);
        DemoCUDRDetailVO detailVO = new DemoCUDRDetailVO();
        BeanUtils.copyProperties(democudr, detailVO);
        return detailVO;
    }

    @Override
    public PageData<DemoCUDRPageVO> pageQuery(DemoCUDRPageQueryDTO queryDTO) {
        Page<DemoCUDRPageVO> p = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        IPage<DemoCUDRPageVO> pageData = democudrMapper.pageQuery(p, queryDTO);
        List<DemoCUDRPageVO> listData = pageData.getRecords();
        return PageData.create(pageData, listData);
    }

    @Override
    public boolean isRepeat(Long id, SFunction<Democudr,?> func, String value) {
        LambdaQueryWrapper<Democudr> lqw = Wrappers.lambdaQuery();
        lqw.eq(Democudr::getDeleted, 0);
        lqw.eq(func, value);
        if (Objects.nonNull(id)) {
            lqw.ne(Democudr::getId, id);
        }
        return super.count(lqw) > 0;
    }

}
