package com.github.codingsoldier.example.bootweb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.codingsoldier.example.bootweb.dto.DemoCUDRPageQueryDTO;
import com.github.codingsoldier.example.bootweb.vo.DemoCUDRPageVO;
import com.github.codingsoldier.example.bootweb.entity.Democudr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * demoCUDR表 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 23:18:09
 */
@Mapper
public interface DemocudrMapper extends BaseMapper<Democudr> {

    /**
     * 分页
     * @param page page
     * @param pageQueryDTO 查询参数
     * @return 分页数据
     */
    IPage<DemoCUDRPageVO> pageQuery(IPage<?> page, @Param("pageQueryDTO") DemoCUDRPageQueryDTO pageQueryDTO);

}
