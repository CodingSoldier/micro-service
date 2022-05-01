package com.github.codingsoldier.bootweb.temp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.codingsoldier.bootweb.temp.dto.DemoPageQueryDto;
import com.github.codingsoldier.bootweb.temp.entity.Demo;
import com.github.codingsoldier.bootweb.temp.vo.DemoPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * demo表 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2022-05-02 01:18:08
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * 分页
     * @param page page
     * @param pageQueryVo 查询参数
     * @return 分页数据
     */
    IPage<DemoPageVo> pageQuery(IPage<?> page, @Param("pageQueryVo") DemoPageQueryDto pageQueryVo);

}
