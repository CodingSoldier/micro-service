package com.github.codingsoldier.bootweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.bootweb.dto.PageDto;
import com.github.codingsoldier.bootweb.entity.UserEntity;
import com.github.codingsoldier.bootweb.vo.UserDetailVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cpq
 * @since 2022-03-17 01:28:55
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页
     *
     * @param pageDto 查询参数
     * @return 分页结果
     */
    IPage<UserDetailVo> pageQuery(PageDto pageDto);

}
