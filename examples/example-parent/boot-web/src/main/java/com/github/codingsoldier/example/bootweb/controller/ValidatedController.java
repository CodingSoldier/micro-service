package com.github.codingsoldier.example.bootweb.controller;


import com.github.codingsoldier.example.bootweb.dto.ValidationDto;
import com.github.codingsoldier.starter.web.util.ValidationUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;




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
    @PostMapping("/bean")
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
    @PostMapping(value = "/bean-method", produces = MediaType.APPLICATION_JSON_VALUE)
    public String beanMethod(@RequestBody ValidationDto validationDto) {
        ValidationUtils.validateEntity(validationDto);
        log.info("请求参数：{}", validationDto);
        return "";
    }

    /**
     * 必须在类上加 @Validated ，get请求参数的校验才生效
     */
    @GetMapping(value = "/param-validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public String paramValidate(@Min(value = 10L, message = "用户id必须大于等于10") Long userId,
                             @Length(min = 6, max = 20, message = "账号长度必须是6~20位。")
                             @NotEmpty(message = "账号不能为空。") String account) {
        log.debug("debug请求参数：{}", userId);
        log.info("请求参数：{}", userId);
        log.info("请求参数：{}", account);
        return "success";
    }

    @GetMapping(value = "/path/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long pathValidate(@PathVariable("id") @Min(value = 10L, message = "id必须大于等于10") Long id) {
        log.info("请求参数：{}", id);
        return id;
    }

}
