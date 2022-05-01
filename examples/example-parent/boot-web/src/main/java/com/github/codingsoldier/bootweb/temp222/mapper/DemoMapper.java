package com.github.codingsoldier.bootweb.temp222.mapper;

import com.github.codingsoldier.bootweb.temp222.entity.Demo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.codingsoldier.bootweb.temp222.vo.DemoPageVo;
import com.github.codingsoldier.bootweb.temp222.dto.DemoPageQueryDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * demo表 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2022-05-02 04:50:06
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
