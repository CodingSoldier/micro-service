package com.github.codingsoldier.bootweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.codingsoldier.bootweb.vo.UserDetailVo;
import com.github.codingsoldier.bootweb.entity.UserEntity;
import com.github.codingsoldier.bootweb.dto.PageDto;
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
public interface UserMapper extends BaseMapper<UserEntity> {

    /**
     * 分页
     * @param page 页号、页码
     * @param pageDto 查询参数
     * @return 分页结果
     */
    IPage<UserDetailVo> pageQuery(IPage<?> page, @Param("pageDto") PageDto pageDto);

}
