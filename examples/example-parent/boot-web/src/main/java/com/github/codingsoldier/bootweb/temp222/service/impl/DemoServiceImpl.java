package com.github.codingsoldier.bootweb.temp222.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.bootweb.temp222.entity.Demo;
import com.github.codingsoldier.bootweb.temp222.mapper.DemoMapper;
import com.github.codingsoldier.bootweb.temp222.service.DemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingsoldier.bootweb.temp222.dto.DemoAddDto;
import com.github.codingsoldier.bootweb.temp222.dto.DemoUpdateDto;
import com.github.codingsoldier.bootweb.temp222.dto.DemoPageQueryDto;
import com.github.codingsoldier.bootweb.temp222.vo.DemoDetailVo;
import com.github.codingsoldier.bootweb.temp222.vo.DemoPageVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * demo表 服务实现类
 * </p>
 *
 * @author cpq
 * @since 2022-05-02 04:50:06
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long add(DemoAddDto addDto) {
        // if (isRepeat(null, Demo::getName, addDto.getName())){
        //     throw new AppException(ResponseCodeEnum.PRECONDITION_FAILED, "新增失败，XX已存在。请修改XX。");
        // }
        Demo demo = new Demo();
        BeanUtils.copyProperties(addDto, demo);
        // 新增
        super.save(demo);
        return demo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long update(DemoUpdateDto updateDto) {
        // if (isRepeat(updateDto.getId(), Demo::getName, updateDto.getName())){
        //     throw new AppException(ResponseCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
        // }
        Demo demo = new Demo();
        BeanUtils.copyProperties(updateDto, demo);
        // 修改
        super.updateById(demo);
        return demo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // 删除
        boolean r = super.removeById(id);
        return r;
    }

    @Override
    public DemoDetailVo detail(Long id) {
        Demo demo = super.getById(id);
        DemoDetailVo detailVo = new DemoDetailVo();
        BeanUtils.copyProperties(demo, detailVo);
        return detailVo;
    }

    @Override
    public PageResult<DemoPageVo> pageQuery(DemoPageQueryDto queryDto) {
        Page<DemoPageVo> p = new Page<>(queryDto.getCurrent(), queryDto.getSize());
        IPage<DemoPageVo> pageData = demoMapper.pageQuery(p, queryDto);
        List<DemoPageVo> listData = pageData.getRecords();
        return PageResult.create(pageData, listData);
    }

    @Override
    public boolean isRepeat(Long id, SFunction<Demo,?> func, String value) {
        LambdaQueryWrapper<Demo> lqw = Wrappers.lambdaQuery();
        lqw.eq(Demo::getDeleted, 0);
        lqw.eq(func, value);
        if (Objects.nonNull(id)) {
            lqw.ne(Demo::getId, id);
        }
        return super.count(lqw) > 0;
    }

}
