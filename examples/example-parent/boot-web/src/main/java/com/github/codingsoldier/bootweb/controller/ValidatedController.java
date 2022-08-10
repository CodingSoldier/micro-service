package com.github.codingsoldier.bootweb.controller;


import com.github.codingsoldier.bootweb.dto.ValidationDto;
import com.github.codingsoldier.starter.web.util.ValidationUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Tag(name = "参数校验-API")
@Slf4j
@RestController
@RequestMapping("/validated")
@Validated
public class ValidatedController {

    /*
     区别      	    @Valid	                 @Validated
    提供者	      JSR-303规范	                Spring
    是否支持分组	     不支持	                     支持
    标注位置	      METHOD, FIELD,             TYPE, METHOD,
                  CONSTRUCTOR, PARAMETER,       PARAMETER
                  TYPE_USE
    嵌套校验	         支持	                     不支持
     */

    /**
     * 使用 @Validated 注解，异常被 @ExceptionHandler(ConstraintViolationException.class) 捕获
     */
    @PostMapping("/bean/")
    public ValidationDto bean(@RequestBody @Validated ValidationDto validationDto) {
        log.info("请求参数：{}", validationDto);
        return validationDto;
    }

    /**
     * 使用 @Valid 注解，异常被 @ExceptionHandler(MethodArgumentNotValidException.class) 捕获
     */
    @PostMapping("/valid/bean")
    public ValidationDto validBean(@RequestBody @Valid ValidationDto validationDto) {
        log.info("请求参数：{}", validationDto);
        return validationDto;
    }

    /**
     * 使用方法校验bean
     */
    @PostMapping("/bean-method")
    public String beanMethod(@RequestBody ValidationDto validationDto) {
        ValidationUtils.validateEntity(validationDto);
        log.info("请求参数：{}", validationDto);
        return "";
    }

    /**
     * 必须在类上加 @Validated ，get请求参数的校验才生效
     */
    @GetMapping("/param-validate")
    public void paramValidate(@Min(value = 10L, message = "用户id必须大于等于10") Long userId,
                             @Length(min = 6, max = 20, message = "账号长度必须是6~20位。")
                             @NotEmpty(message = "账号不能为空。") String account) {
        log.info("请求参数：{}", userId);
        log.info("请求参数：{}", account);
    }

    @GetMapping("/path/{id}")
    public void pathValidate(@PathVariable("id") @Min(value = 10L, message = "id必须大于等于10") Long id) {
        log.info("请求参数：{}", id);
    }

}
