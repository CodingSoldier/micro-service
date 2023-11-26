package com.github.codingsoldier.example.bootweb.temp111.controller;

import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRAddDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRPageQueryDTO;
import com.github.codingsoldier.example.bootweb.temp111.dto.DemoCUDRUpdateDTO;
import com.github.codingsoldier.example.bootweb.temp111.service.DemocudrService;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRDetailVO;
import com.github.codingsoldier.example.bootweb.temp111.vo.DemoCUDRPageVO;
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * demoCUDR表 前端控制器
 * </p>
 *
 * @author cpq
 * @since 2023-11-26 22:12:55
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
    public Result<Void> update(@RequestBody @Valid DemoCUDRUpdateDTO updateDTO) {
        democudrService.update(updateDTO);
        return Result.success();
    }

    @ApiOperationSupport(order = 30)
    @Operation(summary = "删除", description = "返回是否成功")
    @DeleteMapping("/delete")
    public Result<Boolean> delete(@RequestParam("id") @Parameter(description = "主键") Long id) {
        boolean delete = democudrService.delete(id);
        return Result.success(delete);
    }

    @ApiOperationSupport(order = 40)
    @Operation(summary = "详情")
    @GetMapping("/detail")
    public Result<DemoCUDRDetailVO> detail(@RequestParam("id") @Parameter(description = "主键") Long id) {
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
