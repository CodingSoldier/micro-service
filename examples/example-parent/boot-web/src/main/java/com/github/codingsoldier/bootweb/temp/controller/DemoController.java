package com.github.codingsoldier.bootweb.temp.controller;


import com.github.codingsoldier.bootweb.temp.dto.DemoAddDto;
import com.github.codingsoldier.bootweb.temp.dto.DemoPageQueryDto;
import com.github.codingsoldier.bootweb.temp.dto.DemoUpdateDto;
import com.github.codingsoldier.bootweb.temp.service.impl.DemoServiceImpl;
import com.github.codingsoldier.bootweb.temp.vo.DemoDetailVo;
import com.github.codingsoldier.bootweb.temp.vo.DemoPageVo;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * demo表 前端控制器
 * </p>
 *
 * @author cpq
 * @since 2022-05-02 01:18:08
 */
@RestController
@RequestMapping("/demo")
@Api(value = "demo表", tags = "demo表-API")
public class DemoController {

    @Autowired
    private DemoServiceImpl demoService;

    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Long add(@RequestBody @Valid DemoAddDto addDto) {
        Long id = demoService.add(addDto);
        return id;
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public Long update(@RequestBody @Valid DemoUpdateDto updateDto) {
        Long id = demoService.update(updateDto);
        return id;
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        boolean r = demoService.delete(id);
        return r;
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "详情")
    public DemoDetailVo detail(@PathVariable("id") Long id) {
        DemoDetailVo detail = demoService.detail(id);
        return detail;
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页")
    public PageResult<DemoPageVo> pageQuery(@ModelAttribute DemoPageQueryDto queryDto) {
        PageResult<DemoPageVo> pageResult = demoService.pageQuery(queryDto);
        return pageResult;
    }

}
