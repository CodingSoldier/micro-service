package com.github.codingsoldier.example.springdoc.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.example.springdoc.ao.UserCopy1AddUpdateAo;
import com.github.codingsoldier.example.springdoc.entity.UserCopy1;
import com.github.codingsoldier.example.springdoc.vo.UserCopy1DetailVo;

/**
 * <p>
 * 用户表copy 服务类
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 23:19:00
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
    // PageResult<UserCopy1PageVo> pageQuery(UserCopy1PageQueryDto queryDto);

    /**
     * 是否重复
     * @param id id
     * @param func 列函数
     * @param value 列值
     * @return 是否重复
     */
    boolean isRepeat(Long id, SFunction<UserCopy1,?> func, String value);

}
