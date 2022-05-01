package com.github.codingsoldier.bootweb.temp222.controller;

import com.github.codingsoldier.bootweb.temp222.service.DemoService;
import com.github.codingsoldier.bootweb.temp222.dto.DemoAddDto;
import com.github.codingsoldier.bootweb.temp222.dto.DemoUpdateDto;
import com.github.codingsoldier.bootweb.temp222.dto.DemoPageQueryDto;
import com.github.codingsoldier.bootweb.temp222.vo.DemoDetailVo;
import com.github.codingsoldier.bootweb.temp222.vo.DemoPageVo;
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
 * @since 2022-05-02 04:50:06
 */
@RestController
@RequestMapping("/demo")
@Api(value = "demo表", tags = "demo表-API")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @PostMapping("/add")
    @ApiOperation(value = "新增")
    public Long add(@RequestBody @Valid DemoAddDto addDto) {
        return demoService.add(addDto);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public Long update(@RequestBody @Valid DemoUpdateDto updateDto) {
        return demoService.update(updateDto);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        return demoService.delete(id);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "详情")
    public DemoDetailVo detail(@PathVariable("id") Long id) {
        return demoService.detail(id);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页")
    public PageResult<DemoPageVo> pageQuery(@ModelAttribute DemoPageQueryDto queryDto) {
        return demoService.pageQuery(queryDto);
    }

}
