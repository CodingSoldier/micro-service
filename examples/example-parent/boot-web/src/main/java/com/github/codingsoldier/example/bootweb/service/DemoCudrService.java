package com.github.codingsoldier.example.bootweb.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.example.bootweb.entity.DemoCudr;
import com.github.codingsoldier.example.bootweb.mapper.DemoCudrMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrAddDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrPageQueryDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrUpdateDTO;
import com.github.codingsoldier.example.bootweb.vo.DemoCudrDetailVO;
import com.github.codingsoldier.example.bootweb.vo.DemoCudrPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 测试增删改查 服务实现类
 * </p>
 *
 * @author cpq
 * @since 2025-04-11 23:19:52
 */
@Slf4j
@Service
public class DemoCudrService extends ServiceImpl<DemoCudrMapper, DemoCudr> {

  @Autowired
  private DemoCudrMapper demoCudrMapper;

  @Transactional(rollbackFor = Exception.class)
  public Long add(DemoCudrAddDTO addDTO) {
    //if (isRepeat(null, DemoCudr::getName, addDTO.getName())){
    //    throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "新增失败，XX已存在。请修改XX。");
    //}
    DemoCudr demoCudr = new DemoCudr();
    BeanUtils.copyProperties(addDTO, demoCudr);
    super.save(demoCudr);
    return demoCudr.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(DemoCudrUpdateDTO updateDTO) {
    //if (isRepeat(updateDTO.getId(), DemoCudr::getName, updateDTO.getName())){
    //    throw new HttpStatus4xxException(ResultCodeEnum.PRECONDITION_FAILED, "修改失败，XX已存在。请修改XX。");
    //}
    DemoCudr demoCudr = new DemoCudr();
    BeanUtils.copyProperties(updateDTO, demoCudr);
    super.updateById(demoCudr);
  }

  @Transactional(rollbackFor = Exception.class)
  public boolean delete(Long id) {
    return super.removeById(id);
  }

  public DemoCudrDetailVO detail(Long id) {
    DemoCudr demoCudr = super.getById(id);
    DemoCudrDetailVO detailVO = new DemoCudrDetailVO();
    BeanUtils.copyProperties(demoCudr, detailVO);
    return detailVO;
  }

  public Page<DemoCudrPageVO> pageQuery(DemoCudrPageQueryDTO queryDTO) {
    Page<DemoCudrPageVO> p = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
    Page<DemoCudrPageVO> pageData = demoCudrMapper.pageQuery(p, queryDTO);
    return pageData;
  }

  public boolean isRepeat(Long id, SFunction<DemoCudr,?> func, String value) {
    LambdaQueryWrapper<DemoCudr> lqw = Wrappers.lambdaQuery();
    lqw.eq(DemoCudr::getDeleted, 0);
    lqw.eq(func, value);
    if (Objects.nonNull(id)) {
      lqw.ne(DemoCudr::getId, id);
    }
    return super.count(lqw) > 0;
  }

}
