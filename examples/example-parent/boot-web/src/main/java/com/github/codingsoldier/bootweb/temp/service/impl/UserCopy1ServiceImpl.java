package com.github.codingsoldier.bootweb.temp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingsoldier.bootweb.temp.ao.UserCopy1AddUpdateAo;
import com.github.codingsoldier.bootweb.temp.dto.UserCopy1PageQueryDto;
import com.github.codingsoldier.bootweb.temp.entity.UserCopy1;
import com.github.codingsoldier.bootweb.temp.mapper.UserCopy1Mapper;
import com.github.codingsoldier.bootweb.temp.service.UserCopy1Service;
import com.github.codingsoldier.bootweb.temp.vo.UserCopy1DetailVo;
import com.github.codingsoldier.bootweb.temp.vo.UserCopy1PageVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表copy 服务实现类
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 22:39:41
 */
@Slf4j
@Service
public class UserCopy1ServiceImpl extends ServiceImpl<UserCopy1Mapper, UserCopy1> implements UserCopy1Service {

    @Autowired
    private UserCopy1Mapper userCopy1Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addUpdate(UserCopy1AddUpdateAo addUpdateAo) {
        // id 为空，新增；id 不为空，修改
        Long id = addUpdateAo.getId();
        // if (isRepeat(updateDto.getId(), UserCopy1::getName, updateDto.getName())){
        //     throw new AppException(ResponseCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
        // }
        UserCopy1 userCopy1 = new UserCopy1();
        BeanUtils.copyProperties(addUpdateAo, userCopy1);
        if (Objects.isNull(id)){
            // 新增
            super.save(userCopy1);
            id = userCopy1.getId();
        } else {
            // 修改
            super.updateById(userCopy1);
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
    public UserCopy1DetailVo detail(Long id) {
        UserCopy1 userCopy1 = super.getById(id);
        UserCopy1DetailVo detailVo = new UserCopy1DetailVo();
        BeanUtils.copyProperties(userCopy1, detailVo);
        return detailVo;
    }

    @Override
    public PageResult<UserCopy1PageVo> pageQuery(UserCopy1PageQueryDto queryDto) {
        Page<UserCopy1PageVo> p = new Page<>(queryDto.getCurrent(), queryDto.getSize());
        IPage<UserCopy1PageVo> pageData = userCopy1Mapper.pageQuery(p, queryDto);
        List<UserCopy1PageVo> listData = pageData.getRecords();
        return PageResult.create(pageData, listData);
    }

    @Override
    public boolean isRepeat(Long id, SFunction<UserCopy1,?> func, String value) {
        LambdaQueryWrapper<UserCopy1> lqw = Wrappers.lambdaQuery();
        lqw.eq(UserCopy1::getDeleted, 0);
        lqw.eq(func, value);
        if (Objects.nonNull(id)) {
            lqw.ne(UserCopy1::getId, id);
        }
        return super.count(lqw) > 0;
    }

}
