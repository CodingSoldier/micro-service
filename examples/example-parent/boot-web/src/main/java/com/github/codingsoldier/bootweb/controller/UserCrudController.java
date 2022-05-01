package com.github.codingsoldier.bootweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.codingsoldier.bootweb.vo.UserDetailVo;
import com.github.codingsoldier.bootweb.entity.UserEntity;
import com.github.codingsoldier.bootweb.service.UserService;
import com.github.codingsoldier.bootweb.dto.PageDto;
import com.github.codingsoldier.bootweb.dto.UserAddDto;
import com.github.codingsoldier.bootweb.dto.UserUpdateDto;
import com.github.codingsoldier.starter.mybatisplus.resp.PageResult;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.starter.web.util.CopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "用户CURD API")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserCrudController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户-新增")
    @PostMapping("/add")
    public UserDetailVo addUser(@RequestBody UserAddDto userAddDto){
        log.info("请求参数：{}", userAddDto);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userAddDto, userEntity);

        userService.save(userEntity);

        UserEntity userDb = userService.getById(userEntity.getId());
        UserDetailVo userDetailVo = new UserDetailVo();
        BeanUtils.copyProperties(userDb, userDetailVo);

        return userDetailVo;
    }

    @ApiOperation(value = "用户-更新")
    @PutMapping("/update")
    public UserDetailVo updateUser(@RequestBody UserUpdateDto userUpdateDto){
        log.info("请求参数：{}", userUpdateDto);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userUpdateDto, userEntity);

        userService.updateById(userEntity);

        UserEntity userDb = userService.getById(userEntity.getId());
        UserDetailVo userDetailVo = new UserDetailVo();
        BeanUtils.copyProperties(userDb, userDetailVo);

        return userDetailVo;
    }

    @ApiOperation(value = "用户-删除")
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id")Long id){
        log.info("请求参数：{}", id);
        boolean b = userService.removeById(id);
        return b;
    }

    @ApiOperation(value = "用户-分页")
    @GetMapping("/page")
    public PageResult<UserDetailVo> page(@ModelAttribute PageDto pageDto){
        log.info("请求参数：{}", pageDto);
        LambdaQueryWrapper<UserEntity> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(pageDto.getName()), UserEntity::getName, pageDto.getName());
        lqw.like(Objects.nonNull(pageDto.getAge()), UserEntity::getAge, pageDto.getAge());
        Page<UserEntity> p = new Page<>(pageDto.getCurrent(), pageDto.getSize());

        Page<UserEntity> result = userService.page(p, lqw);

        List<UserDetailVo> userDetailVoList = CopyUtils.listCopy(result.getRecords(), UserDetailVo.class);

        return PageResult.create(result, userDetailVoList);
    }

    @ApiOperation(value = "用户-分页-自己写")
    @GetMapping("/page/test")
    public PageResult<UserDetailVo> pageMapper(@ModelAttribute PageDto pageDto){
        IPage<UserDetailVo> result = userService.pageQuery(pageDto);
        return PageResult.create(result, result.getRecords());
    }

    @ApiOperation(value = "用户-详情")
    @GetMapping("/detail/{id}")
    public UserDetailVo page(@PathVariable("id")Long id){
        UserEntity userEntity = userService.getById(id);
        UserDetailVo userDetailVo = new UserDetailVo();
        BeanUtils.copyProperties(userEntity, userDetailVo);
        return userDetailVo;
    }

    @ApiOperation(value = "swagger测试")
    @GetMapping("/test-swagger/{id}")
    public Result<PageResult<UserDetailVo>> testSwagger(@PathVariable("id")Long id){
        return null;
    }


}
