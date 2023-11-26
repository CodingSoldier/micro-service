package com.github.codingsoldier.example.bootweb.temp111.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRAddDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRPageQueryDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRUpdateDTO;
import com.github.codingsoldier.example.bootweb.temp111.entity.Democudr;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRDetailVO;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRPageVO;
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;

/**
 * <p>
 * demoCUDR表 服务类
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 22:24:47
 */
public interface DemocudrService extends IService<Democudr> {

    /**
     * 新增
     * @param addDTO 新增参数
     * @return id
     */
    Long add(DemoCUDRAddDTO addDTO);

    /**
     * 修改
     * @param updateDTO 修改参数
     */
    void update(DemoCUDRUpdateDTO updateDTO);

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
    DemoCUDRDetailVO detail(Long id);

    /**
     * 分页
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    PageData<DemoCUDRPageVO> pageQuery(DemoCUDRPageQueryDTO queryDTO);

    /**
     * 是否重复
     * @param id id
     * @param func 列函数
     * @param value 列值
     * @return 是否重复
     */
    boolean isRepeat(Long id, SFunction<Democudr,?> func, String value);

}
