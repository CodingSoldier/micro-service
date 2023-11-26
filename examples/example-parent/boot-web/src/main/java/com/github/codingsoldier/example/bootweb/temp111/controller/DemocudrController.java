package com.github.codingsoldier.example.bootweb.temp111.controller;

import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.bootweb.temp111.service.DemocudrService;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRAddDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRUpdateDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRPageQueryDTO;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRDetailVO;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRPageVO;
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * demoCUDR表 前端控制器
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 21:51:37
 */
@ApiSupport(order = 100)
@Tag(name = "demoCUDR表-API")
@Slf4j
@RestController
@RequestMapping("/democudr")
public class DemocudrController {

    private final DemocudrService democudrService;

    public DemocudrController(DemocudrService democudrService) {
        this.democudrService = democudrService;
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "新增", description = "返回id")
    @PostMapping("/add")
    public Result<Long> add(@RequestBody @Valid DemoCUDRAddDTO addDTO) {
        Long id = democudrService.add(addDTO);
        return Result.success(id);
    }

    @ApiOperationSupport(order = 20)
    @Operation(summary = "修改")
    @PutMapping("/update")
    public Result<?> update(@RequestBody @Valid DemoCUDRUpdateDTO updateDTO) {
        democudrService.update(updateDTO);
        return Result.success();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "删除", description = "返回是否成功")
    @Parameters({
        @Parameter(name = "id",description = "id"),
    })
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestParam("id") Long id) {
        boolean delete = democudrService.delete(id);
        return Result.success(delete);
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "详情")
    @Parameters({
        @Parameter(name = "id",description = "id"),
    })
    @GetMapping("/detail")
    public Result<DemoCUDRDetailVO> detail(@RequestParam("id") Long id) {
        DemoCUDRDetailVO detail = democudrService.detail(id);
        return Result.success(detail);
    }

    @ApiOperationSupport(order = 50)
    @Operation(summary = "分页")
    @PostMapping("/page")
    public Result<PageData<DemoCUDRPageVO>> pageQuery(@RequestBody DemoCUDRPageQueryDTO queryDTO) {
        PageData<DemoCUDRPageVO> pageData = democudrService.pageQuery(queryDTO);
        return Result.success(pageData);
    }

}
