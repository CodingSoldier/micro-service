package com.github.codingsoldier.example.bootweb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrAddDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrPageQueryDTO;
import com.github.codingsoldier.example.bootweb.dto.DemoCudrUpdateDTO;
import com.github.codingsoldier.example.bootweb.service.DemoCudrService;
import com.github.codingsoldier.example.bootweb.vo.DemoCudrDetailVO;
import com.github.codingsoldier.example.bootweb.vo.DemoCudrPageVO;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试增删改查 前端控制器
 *
 * @author cpq
 * @since 2025-04-11 23:19:52
 */
@ApiSupport(order = 100)
@Tag(name = "测试增删改查-API")
@Slf4j
@RestController
@RequestMapping("/demo-cudr")
public class DemoCudrController {

  @Autowired
  private DemoCudrService demoCudrService;

  @ApiOperationSupport(order = 10)
  @Operation(summary = "新增", description = "返回id")
  @PostMapping("/add")
  public Result<Long> add(@RequestBody @Valid DemoCudrAddDTO addDTO) {
    Long id = demoCudrService.add(addDTO);
    return Result.success(id);
  }

  @ApiOperationSupport(order = 20)
  @Operation(summary = "修改")
  @PostMapping("/update")
  public Result<Void> update(@RequestBody @Valid DemoCudrUpdateDTO updateDTO) {
    demoCudrService.update(updateDTO);
    return Result.success();
  }

  @ApiOperationSupport(order = 30)
  @Operation(summary = "删除", description = "返回是否成功")
  @DeleteMapping("/delete")
  public Result<Boolean> delete(@RequestParam("id") @Parameter(description = "主键") Long id) {
    boolean delete = demoCudrService.delete(id);
    return Result.success(delete);
  }

  @ApiOperationSupport(order = 40)
  @Operation(summary = "详情")
  @GetMapping("/detail")
  public Result<DemoCudrDetailVO> detail(@RequestParam("id") @Parameter(description = "主键") Long id) {
    DemoCudrDetailVO detail = demoCudrService.detail(id);
    return Result.success(detail);
  }

  @ApiOperationSupport(order = 50)
  @Operation(summary = "分页")
  @PostMapping("/page")
  public Result<Page<DemoCudrPageVO>> pageQuery(@RequestBody DemoCudrPageQueryDTO queryDTO) {
    Page<DemoCudrPageVO> pageData = demoCudrService.pageQuery(queryDTO);
    return Result.success(pageData);
  }

}
