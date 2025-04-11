package com.github.codingsoldier.example.bootweb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrPageQueryDTO;
import com.github.codingsoldier.example.bootweb.vo.DemoCudrPageVO;
import com.github.codingsoldier.example.bootweb.entity.DemoCudr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * <p>
 * 测试增删改查 Mapper 接口
 * </p>
 *
 * @author cpq
 * @since 2025-04-11 23:19:52
 */
@Mapper
public interface DemoCudrMapper extends BaseMapper<DemoCudr> {

  /**
   * 分页
   * @param page page
   * @param pageQueryDTO 查询参数
   * @return 分页数据
   */
  Page<DemoCudrPageVO> pageQuery(IPage<?> page, @Param("pageQueryDTO") DemoCudrPageQueryDTO pageQueryDTO);

}
