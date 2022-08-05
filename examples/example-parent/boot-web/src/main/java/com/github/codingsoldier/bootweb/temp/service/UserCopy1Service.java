package com.github.codingsoldier.bootweb.temp.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.bootweb.temp.ao.UserCopy1AddUpdateAo;
import com.github.codingsoldier.bootweb.temp.dto.UserCopy1PageQueryDto;
import com.github.codingsoldier.bootweb.temp.entity.UserCopy1;
import com.github.codingsoldier.bootweb.temp.vo.UserCopy1DetailVo;
import com.github.codingsoldier.bootweb.temp.vo.UserCopy1PageVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;

/**
 * <p>
 * 用户表copy 服务类
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 22:39:41
 */
public interface UserCopy1Service extends IService<UserCopy1> {

    /**
     * 新增/修改
     * @param addUpdateAo 新增/修改 参数
     * @return id
     */
    Long addUpdate(UserCopy1AddUpdateAo addUpdateAo);


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
    UserCopy1DetailVo detail(Long id);

    /**
     * 分页
     * @param queryDto 查询参数
     * @return 分页结果
     */
    PageResult<UserCopy1PageVo> pageQuery(UserCopy1PageQueryDto queryDto);

    /**
     * 是否重复
     * @param id id
     * @param func 列函数
     * @param value 列值
     * @return 是否重复
     */
    boolean isRepeat(Long id, SFunction<UserCopy1,?> func, String value);

}
