package com.github.codingsoldier.bootweb.temp.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.bootweb.temp.dto.DemoAddDto;
import com.github.codingsoldier.bootweb.temp.dto.DemoPageQueryDto;
import com.github.codingsoldier.bootweb.temp.dto.DemoUpdateDto;
import com.github.codingsoldier.bootweb.temp.entity.Demo;
import com.github.codingsoldier.bootweb.temp.vo.DemoDetailVo;
import com.github.codingsoldier.bootweb.temp.vo.DemoPageVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;

/**
 * <p>
 * demo表 服务类
 * </p>
 *
 * @author cpq
 * @since 2022-05-02 01:18:08
 */
public interface DemoService extends IService<Demo> {

    /**
     * 新增
     * @param addDto 新增参数
     * @return id
     */
    Long add(DemoAddDto addDto);

    /**
     * 修改
     * @param updateDto 更新参数
     * @return id
     */
    Long update(DemoUpdateDto updateDto);

    /**
     * 删除
     * @param id id
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 详情
     * @param id id
     * @return 详情
     */
    DemoDetailVo detail(Long id);

    /**
     * 分页
     * @param queryDto 查询参数
     * @return 分页结果
     */
    PageResult<DemoPageVo> pageQuery(DemoPageQueryDto queryDto);

    /**
     * 是否重复
     * @param id
     * @param columnValue
     * @return
     */
    boolean isRepeat(Long id, SFunction<Demo,?> columnFunction, String columnValue);


}
