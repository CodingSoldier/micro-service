package com.github.codingsoldier.example.bootweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.example.bootweb.dto.UserDTO;
import com.github.codingsoldier.example.bootweb.dto.UserAddDTO;
import com.github.codingsoldier.example.bootweb.dto.UserUpdateDTO;
import com.github.codingsoldier.example.bootweb.entity.User;
import com.github.codingsoldier.example.bootweb.service.UserService;
import com.github.codingsoldier.example.bootweb.vo.UserDetailVO;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.starter.mybatisplus.resp.PageData;
import com.github.codingsoldier.starter.web.util.CopyUtils;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cpq
 * @since 2022-03-17 04:10:40
 */
@ApiSupport(order = 1111111)
@Tag(name = "用户CURD-API")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserCrudController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户-新增")
    @PostMapping("/add")
    public UserDetailVO addUser(@RequestBody UserAddDTO userAddDto) {
        log.info("请求参数：{}", userAddDto);
        User userEntity = new User();
        BeanUtils.copyProperties(userAddDto, userEntity);

        userService.save(userEntity);

        User userDb = userService.getById(userEntity.getId());
        UserDetailVO userDetailVo = new UserDetailVO();
        BeanUtils.copyProperties(userDb, userDetailVo);

        return userDetailVo;
    }

    @Operation(summary = "用户-更新")
    @PutMapping("/update")
    public UserDetailVO updateUser(@RequestBody UserUpdateDTO userUpdateDto) {
        log.info("请求参数：{}", userUpdateDto);
        User userEntity = new User();
        BeanUtils.copyProperties(userUpdateDto, userEntity);

        userService.updateById(userEntity);

        User userDb = userService.getById(userEntity.getId());
        UserDetailVO userDetailVo = new UserDetailVO();
        BeanUtils.copyProperties(userDb, userDetailVo);

        return userDetailVo;
    }

    @Operation(summary = "用户-删除")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        log.info("请求参数：{}", id);
        boolean b = userService.removeById(id);
        return b;
    }

    @Operation(summary = "用户-分页")
    @GetMapping("/page")
    public PageData<UserDetailVO> page(@ModelAttribute @ParameterObject UserDTO pageDto) {
        log.info("请求参数：{}", pageDto);
        LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(pageDto.getName()), User::getName, pageDto.getName());
        lqw.like(Objects.nonNull(pageDto.getAge()), User::getAge, pageDto.getAge());
        Page<User> p = new Page<>(pageDto.getCurrent(), pageDto.getSize());

        Page<User> result = userService.page(p, lqw);

        List<UserDetailVO> userDetailVoList = CopyUtils.listCopy(result.getRecords(), UserDetailVO.class);

        return PageData.create(result, userDetailVoList);
    }

    @Operation(summary = "用户-分页-自己写")
    @GetMapping("/page/test")
    public PageData<UserDetailVO> pageMapper(@ModelAttribute UserDTO pageDto) {
        IPage<UserDetailVO> result = userService.pageQuery(pageDto);
        return PageData.create(result, result.getRecords());
    }

    @Operation(summary = "用户-详情")
    @GetMapping("/detail/{id}")
    public UserDetailVO page(@PathVariable("id") Long id) {
        User userEntity = userService.getById(id);
        UserDetailVO userDetailVo = new UserDetailVO();
        BeanUtils.copyProperties(userEntity, userDetailVo);
        return userDetailVo;
    }

    @Operation(summary = "swagger测试")
    @GetMapping("/test-swagger/{id}")
    public Result<PageData<UserDetailVO>> testSwagger(@PathVariable("id") Long id) {
        return null;
    }


}
