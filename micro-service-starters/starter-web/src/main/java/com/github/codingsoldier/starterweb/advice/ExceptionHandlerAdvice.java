package com.github.codingsoldier.starterweb.advice;


import com.github.codingsoldier.common.enums.RespCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.util.StringUtils;
import com.github.codingsoldier.starterweb.resp.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice(value = "com.github.codingsoldier")
public class ExceptionHandlerAdvice {

	/**
	 * AppException
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	public Result appExceptionHandler(final AppException ex) {
		log.error("AppException", ex);
		return Result.fail(ex.getCode(), ex.getMessage());
	}

	/**
	 * 转换对象类Request的校验失败结果
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseBody
	public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.error("参数校验异常", ex);
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
		return Result.fail(RespCodeEnum.PRECONDITION_FAILED.getCode(), msg);
	}

	/**
	 * 转换单一属性Request的校验失败结果，如string，int等
	 * @param ex
	 * @return
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseBody
	public Result handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("参数校验异常", ex);
		StringBuilder sb = new StringBuilder();
		ex.getConstraintViolations().stream().forEach(e -> {
			String message = e.getMessage();
			boolean isMatch = StringUtils.isEndWith(message, StringUtils.END_CHAR);
			// 没有结尾符号，添加句号
			message = isMatch ? message : String.format("%s。", message);
			sb.append(message);
		});
		String msg = sb.toString();
		return Result.fail(RespCodeEnum.PRECONDITION_FAILED.getCode(), msg);
	}

	@ExceptionHandler(value =NullPointerException.class)
	public Result nullPointerExceptionHandler(final NullPointerException ex){
		log.error("空指针异常", ex);
		return Result.fail(RespCodeEnum.ERROR.getCode(), "空指针异常。");
	}

	/**
	 * 缺少请求参数异常
	 * @param ex HttpMessageNotReadableException
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result missingParameterExceptionHandle(MissingServletRequestParameterException ex) {
		log.error("缺少请求参数", ex);
		return Result.fail("缺少请求参数。");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result noHandlerFoundExceptionHandle(NoHandlerFoundException ex) {
		log.error("404异常", ex);
		return Result.fail("404未找到资源。");
	}


	@ExceptionHandler(HttpMessageConversionException.class)
	public Result httpMessageNotReadableException(final HttpMessageConversionException ex) {
		log.error("数据转换异常", ex);
		return Result.fail(RespCodeEnum.ERROR.getCode(), "数据转换异常。");
	}

	/**
	 * 请求方法错误。例如 /a 只支持 POST 请求, 前端却使用 GET 请求进行访问
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result httpRequestMethodNotSupportedExceptionHandler(final HttpRequestMethodNotSupportedException ex) {
		log.error("请求方法错误", ex);
		return Result.fail(RespCodeEnum.ERROR.getCode(), "请求方法错误。");
	}

	/**
	 * 参数类型转换失败时抛出异常
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Result methodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
		log.error("参数类型错误", ex);
		return Result.fail(RespCodeEnum.ERROR.getCode(),"参数类型错误。");
	}

	/**
	 * Exception异常处理
	 */
	@ExceptionHandler(Exception.class)
	public Result exception(final Exception ex) {
		log.error("异常", ex);
		return Result.fail(RespCodeEnum.ERROR.getCode(),"处理请求失败。");
	}

}
