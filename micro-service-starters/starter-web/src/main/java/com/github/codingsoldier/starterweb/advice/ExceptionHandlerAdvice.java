package com.github.codingsoldier.starterweb.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import com.github.codingsoldier.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
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

import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice(value = "com.github.codingsoldier")
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionHandlerAdvice {

	/**
	 * AppException
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result appExceptionHandler(final AppException ex) {
		log.error("捕获AppException", ex);
		return Result.fail(ex.getCode(), ex.getMessage());
	}

	/**
	 * 转换对象类Request的校验失败结果
	 * 转换单一属性Request的校验失败结果，如string，int等
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result validExceptionHandler(MethodArgumentNotValidException ex) {
		log.error("捕获参数校验异常", ex);
		StringBuilder sb = new StringBuilder();
		BindingResult bindingResult = ex.getBindingResult();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String defaultMessage = fieldError.getDefaultMessage();
			boolean isMatch = StringUtils.isEndWith(defaultMessage, StringUtils.END_CHAR);
			// 没有结尾符号，添加句号
			defaultMessage = isMatch ? defaultMessage : String.format("%s。", defaultMessage);
			sb.append(defaultMessage);
		}
		String msg = sb.toString();
		return Result.fail(ResponseCodeEnum.PRECONDITION_FAILED.getCode(), msg);
	}

	/**
	 * 请求方法错误。例如 /a 只支持 POST 请求, 前端却使用 GET 请求进行访问
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result httpRequestMethodNotSupportedExceptionHandler(final HttpRequestMethodNotSupportedException ex) {
		log.error("捕获请求方法错误", ex);
		return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "请求方法错误。");
	}

	/**
	 * 参数类型转换失败时抛出异常
	 */
	@ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageConversionException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result methodArgumentTypeMismatchExceptionHandler(final MethodArgumentTypeMismatchException ex) {
		log.error("捕获参数类型错误", ex);
		return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(),"参数类型错误。");
	}

	/**
	 * 缺少请求参数异常
	 * @param ex HttpMessageNotReadableException
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result missingParameterExceptionHandle(MissingServletRequestParameterException ex) {
		log.error("捕获缺少请求参数异常", ex);
		return Result.fail("缺少请求参数。");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Result noHandlerFoundExceptionHandle(NoHandlerFoundException ex) {
		log.error("捕获404异常", ex);
		return Result.fail("404未找到资源。");
	}

	@ExceptionHandler(value = IOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result nullPointerExceptionHandler(final IOException ex){
		log.error("捕获IO异常", ex);
		return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "IO异常。");
	}

	@ExceptionHandler(value =NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result nullPointerExceptionHandler(final NullPointerException ex){
		log.error("捕获空指针异常", ex);
		return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(), "空指针异常。");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result exceptionHandler(final Exception ex) {
		log.error("捕获异常", ex);
		return Result.fail(ResponseCodeEnum.SERVER_ERROR.getCode(),"处理请求失败。");
	}

}
