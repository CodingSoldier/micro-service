package com.github.codingsoldier.starter.web.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.exception.ResultNotSuccessFeignException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Set;

/**
 * 统一异常处理
 * 自定义全局异常处理器加上 @Order(Ordered.HIGHEST_PRECEDENCE) 注解，即可覆盖此全局异常处理器
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionHandlerAdvice {

    /**
     * AppException
     *
     * @param ex ex
     * @return result
     */
    @ExceptionHandler(AppException.class)
    public Result<Object> appExceptionHandler(final AppException ex) {
        log.error("捕获AppException", ex);
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * ResultNotSuccessFeignException
     *
     * @param ex ex
     * @return result
     */
    @ExceptionHandler(ResultNotSuccessFeignException.class)
    public Result<Object> resultNotSuccessFeignException(final ResultNotSuccessFeignException ex) {
        log.error("捕获ResultNotSuccessFeignException", ex);
        return Result.fail(ex.getCode(), ex.getMessage());
    }


    /**
     * 转换对象类Request的校验失败结果
     * 转换单一属性Request的校验失败结果，如string，int等
     *
     * @param ex ex
     * @return result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("捕获MethodArgumentNotValidException异常", ex);
        StringBuilder sb = new StringBuilder();
        BindingResult bindingResult = ex.getBindingResult();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            boolean isMatch = StringUtil.isEndWith(defaultMessage, StringUtil.END_CHAR);
            // 没有结尾符号，添加句号
            defaultMessage = isMatch ? defaultMessage : String.format("%s。", defaultMessage);
            sb.append(defaultMessage);
        }
        String msg = sb.toString();
        return Result.fail(ResponseCodeEnum.PRECONDITION_FAILED.getCode(), msg);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> constraintViolationException(ConstraintViolationException ex) {
        log.error("捕获ConstraintViolationException异常", ex);
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        if (!constraintViolations.isEmpty()) {
            for (ConstraintViolation<?> constraint : constraintViolations) {
                String message = constraint.getMessage();
                boolean isMatch = StringUtil.isEndWith(message, StringUtil.END_CHAR);
                // 没有结尾符号，添加句号
                message = isMatch ? message : String.format("%s。", message);
                sb.append(message);
            }
        }
        String msg = sb.toString();
        return Result.fail(ResponseCodeEnum.PRECONDITION_FAILED.getCode(), msg);
    }

    /**
     * 请求方法错误。例如 /a 只支持 POST 请求, 前端却使用 GET 请求进行访问
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> httpRequestMethodNotSupportedExceptionHandler(final HttpRequestMethodNotSupportedException ex) {
        log.error("捕获请求方法错误", ex);
        return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "请求方法错误。");
    }

    /**
     * 参数类型转换失败时抛出异常
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class})
    public Result<Object> methodArgumentTypeMismatchExceptionHandler(final Exception ex) {
        log.error("捕获参数类型错误", ex);
        return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "参数类型错误。");
    }

    /**
     * 缺少请求参数异常
     *
     * @param ex HttpMessageNotReadableException
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> missingParameterExceptionHandle(MissingServletRequestParameterException ex) {
        log.error("捕获缺少请求参数异常", ex);
        return Result.fail("缺少请求参数"+ex.getParameterName());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Object> noHandlerFoundExceptionHandle(NoHandlerFoundException ex) {
        log.error("捕获404异常NoHandlerFoundException", ex);
        return Result.fail("404未找到资源。");
    }

    @ExceptionHandler(value = IOException.class)
    public Result<Object> iOException(final IOException ex) {
        log.error("捕获IOException", ex);
        return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "IO异常。");
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Result<Object> nullPointerExceptionHandler(final NullPointerException ex) {
        log.error("捕获空指针异常", ex);
        return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "空指针异常。");
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(final Exception ex) {
        log.error("捕获异常", ex);
        return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), ResponseCodeEnum.SERVER_ERROR.getMessage());
    }

}
