package com.github.codingsoldier.example.bootweb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingsoldier.example.bootweb.dto.UserDTO;
import com.github.codingsoldier.example.bootweb.entity.User;
import com.github.codingsoldier.example.bootweb.mapper.UserMapper;
import com.github.codingsoldier.example.bootweb.service.UserService;
import com.github.codingsoldier.example.bootweb.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cpq
 * @since 2022-03-17 01:28:55
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public IPage<UserDetailVO> pageQuery(UserDTO pageDto) {
        Page<?> p = new Page<>(pageDto.getCurrent(), pageDto.getSize());
        return userMapper.pageQuery(p, pageDto);
    }

}
