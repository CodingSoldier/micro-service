package com.github.codingsoldier.example.bootweb.temp111.mapper;

import com.github.codingsoldier.example.bootweb.temp111.entity.Democudr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRPageVO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRPageQueryDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * demoCUDR表 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 21:51:37
 */
@Mapper
public interface DemocudrMapper extends BaseMapper<Democudr> {

    /**
     * 分页
     * @param page page
     * @param pageQueryVO 查询参数
     * @return 分页数据
     */
    IPage<DemoCUDRPageVO> pageQuery(IPage<?> page, @Param("pageQueryVO") DemoCUDRPageQueryDTO pageQueryVO);

}
