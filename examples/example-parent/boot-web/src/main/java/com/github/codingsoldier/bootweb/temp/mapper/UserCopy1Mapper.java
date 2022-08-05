package com.github.codingsoldier.bootweb.temp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.codingsoldier.bootweb.temp.dto.UserCopy1PageQueryDto;
import com.github.codingsoldier.bootweb.temp.entity.UserCopy1;
import com.github.codingsoldier.bootweb.temp.vo.UserCopy1PageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 用户表copy Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 22:39:41
 */
@Mapper
public interface UserCopy1Mapper extends BaseMapper<UserCopy1> {

    /**
     * 分页
     * @param page page
     * @param pageQueryVo 查询参数
     * @return 分页数据
     */
    IPage<UserCopy1PageVo> pageQuery(IPage<?> page, @Param("pageQueryVo") UserCopy1PageQueryDto pageQueryVo);

}
