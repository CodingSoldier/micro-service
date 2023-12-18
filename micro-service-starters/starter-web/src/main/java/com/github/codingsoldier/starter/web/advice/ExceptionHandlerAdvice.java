package com.github.codingsoldier.starter.web.advice;


import com.github.codingsoldier.common.constant.OrderConstant;
import com.github.codingsoldier.common.enums.ResultCodeEnum;
import com.github.codingsoldier.common.exception.HttpStatus5xxException;
import com.github.codingsoldier.common.exception.HttpStatus4xxException;
import com.github.codingsoldier.common.exception.MicroServiceException;
import com.github.codingsoldier.common.exception.feign.FeignResultErrorException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.CommonUtil;
import com.github.codingsoldier.common.util.StringUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.Set;

/**
 * 统一异常处理
 * 自定义全局异常处理器加上 @Order(Ordered.HIGHEST_PRECEDENCE) 注解，即可覆盖此全局异常处理器
 * 注意：如果您在自己自定义的全局异常处理器中添加了 @ExceptionHandler(Exception.class) ，
 *      你的全局异常处理器将捕获所有异常，相当于本异常处理器失效。
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Slf4j
@RestControllerAdvice
@Order(OrderConstant.ADVICE_EXCEPTION)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(HttpStatus4xxException.class)
    public Result<Object> httpStatus4xxException(final HttpStatus4xxException ex, HttpServletResponse response) {
        log.error("捕获HttpStatus4xxException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(HttpStatus5xxException.class)
    public Result<Object> httpStatus5xxException(final HttpStatus5xxException ex, HttpServletResponse response) {
        log.error("捕获HttpStatus5xxException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MicroServiceException.class)
    public Result<Object> microServiceException(final MicroServiceException ex, HttpServletResponse response) {
        log.error("捕获MicroServiceException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(FeignResultErrorException.class)
    public Result<Object> resultNotSuccessFeignException(final FeignResultErrorException ex, HttpServletResponse response) {
        log.error("捕获ResultNotSuccessFeignException", ex);
        response.setStatus(CommonUtil.getResponseStatus(ex.getCode()));
        return Result.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 使用 @Valid 注解，被此方法捕获
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
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
        int code = ResultCodeEnum.PRECONDITION_FAILED.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, msg);
    }

    /**
     * 使用 @Validated 注解，被此方法捕获
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Object> constraintViolationException(ConstraintViolationException ex, HttpServletResponse response) {
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
        int code = ResultCodeEnum.PRECONDITION_FAILED.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, msg);
    }

    /**
     * 请求方法错误。例如 /a 只支持 POST 请求, 前端却使用 GET 请求进行访问
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> httpRequestMethodNotSupportedExceptionHandler(final HttpRequestMethodNotSupportedException ex, HttpServletResponse response) {
        log.error("捕获请求方法错误", ex);
        int code = ResultCodeEnum.BAD_REQUEST.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, "请求方法错误。");
    }

    /**
     * 参数类型转换失败时抛出异常
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class})
    public Result<Object> methodArgumentTypeMismatchExceptionHandler(final Exception ex, HttpServletResponse response) {
        log.error("捕获参数类型错误", ex);
        int code = ResultCodeEnum.BAD_REQUEST.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, "参数类型错误。");
    }

    /**
     * 缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> missingParameterExceptionHandle(MissingServletRequestParameterException ex, HttpServletResponse response) {
        log.error("捕获缺少请求参数异常", ex);
        int code = ResultCodeEnum.BAD_REQUEST.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code,"缺少请求参数: "+ex.getParameterName());
    }

    /**
     * 默认情况下，当DispatcherServlet找不到请求的处理程序时，它会发送一个404响应。
     * 但是，如果它的属性"throwExceptionIfNoHandlerFound"被设置为true，则会引发此异常，并可以使用配置的HandlerExceptionResolver来处理。
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result<Object> noHandlerFoundExceptionHandle(NoHandlerFoundException ex) {
        log.error("捕获404异常NoHandlerFoundException", ex);
        return Result.fail(HttpStatus.NOT_FOUND.value() * 100,"404未找到资源。");
    }

    @ExceptionHandler(value = IOException.class)
    public Result<Object> iOException(final IOException ex, HttpServletResponse response) {
        log.error("捕获IOException", ex);
        int code = ResultCodeEnum.SERVER_ERROR.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, "IO异常");
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Result<Object> nullPointerExceptionHandler(final NullPointerException ex, HttpServletResponse response) {
        log.error("捕获空指针异常", ex);
        int code = ResultCodeEnum.SERVER_ERROR.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, "空指针异常");
    }

    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(final Exception ex, HttpServletResponse response) {
        log.error("捕获异常", ex);
        int code = ResultCodeEnum.SERVER_ERROR.getCode();
        response.setStatus(CommonUtil.getResponseStatus(code));
        return Result.fail(code, ResultCodeEnum.SERVER_ERROR.getMessage());
    }

}
