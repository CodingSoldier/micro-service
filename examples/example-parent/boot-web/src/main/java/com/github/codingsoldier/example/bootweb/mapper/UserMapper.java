package com.github.codingsoldier.example.bootweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.codingsoldier.example.bootweb.dto.UserDTO;
import com.github.codingsoldier.example.bootweb.entity.User;
import com.github.codingsoldier.example.bootweb.vo.UserDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2022-03-17 01:28:55
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页
     *
     * @param page    页号、页码
     * @param pageDto 查询参数
     * @return 分页结果
     */
    IPage<UserDetailVO> pageQuery(IPage<?> page, @Param("pageDto") UserDTO pageDto);

}
