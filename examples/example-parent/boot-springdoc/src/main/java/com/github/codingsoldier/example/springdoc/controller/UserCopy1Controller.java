package com.github.codingsoldier.example.springdoc.controller;

import com.github.codingsoldier.example.springdoc.ao.UserCopy1AddUpdateAo;
import com.github.codingsoldier.example.springdoc.dto.UserCopy1AddDto;
import com.github.codingsoldier.example.springdoc.dto.UserCopy1UpdateDto;
import com.github.codingsoldier.example.springdoc.service.UserCopy1Service;
import com.github.codingsoldier.example.springdoc.vo.UserCopy1DetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表copy 前端控制器
 * </p>
 *
 * @author cpq
 * @since 2022-08-04 23:19:00
 */
@Slf4j
@RestController
@RequestMapping("/user-copy1")
@Tag(name = "用户表copy-API")
public class UserCopy1Controller {

    @Autowired
    private UserCopy1Service userCopy1Service;

    @PostMapping("/add")
    @Operation(summary =  "新增")
    public Long add(@RequestBody @Valid UserCopy1AddDto addDto) {
        UserCopy1AddUpdateAo addAo = new UserCopy1AddUpdateAo();
        BeanUtils.copyProperties(addDto, addAo);
        return userCopy1Service.addUpdate(addAo);
    }

    @PutMapping("/update")
    @Operation(summary = "修改")
    public Long update(@RequestBody @Valid UserCopy1UpdateDto updateDto) {
        UserCopy1AddUpdateAo updateAo = new UserCopy1AddUpdateAo();
        BeanUtils.copyProperties(updateDto, updateAo);
        return userCopy1Service.addUpdate(updateAo);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除")
    public boolean delete(@PathVariable("id") Long id) {
        return userCopy1Service.delete(id);
    }

    @GetMapping("/detail/{id}")
    @Operation(summary = "详情")
    public UserCopy1DetailVo detail(@PathVariable("id") Long id) {
        return userCopy1Service.detail(id);
    }

    // @GetMapping("/page")
    // @Operation(summary = "分页")
    // public PageResult<UserCopy1PageVo> pageQuery(@ModelAttribute UserCopy1PageQueryDto queryDto) {
    //     return userCopy1Service.pageQuery(queryDto);
    // }

}
