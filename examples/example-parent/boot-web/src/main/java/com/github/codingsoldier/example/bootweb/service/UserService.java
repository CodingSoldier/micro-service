package com.github.codingsoldier.example.bootweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.example.bootweb.dto.UserDTO;
import com.github.codingsoldier.example.bootweb.entity.User;
import com.github.codingsoldier.example.bootweb.vo.UserDetailVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cpq
 * @since 2022-03-17 01:28:55
 */
public interface UserService extends IService<User> {

    /**
     * 分页
     *
     * @param pageDto 查询参数
     * @return 分页结果
     */
    IPage<UserDetailVO> pageQuery(UserDTO pageDto);

}
