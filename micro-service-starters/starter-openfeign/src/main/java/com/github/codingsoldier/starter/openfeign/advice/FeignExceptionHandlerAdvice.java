package com.github.codingsoldier.starter.openfeign.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.exception.AppException;
import com.github.codingsoldier.common.resp.Result;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * feign异常处理，@Order需要比
 * com.github.codingsoldier.starterweb.advice.ExceptionHandlerAdvice
 * 小
 */
@Slf4j
@RestControllerAdvice(value = "com.github.codingsoldier")
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class FeignExceptionHandlerAdvice {

	@ExceptionHandler(value = HystrixRuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result hystrixRuntimeExceptionHandler(final HystrixRuntimeException ex){
		log.error("捕获hystrix运行时异常", ex);
		if (ex.getCause() instanceof AppException){
			return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), ex.getCause().getMessage());
		} else {
			return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务间调用异常。");
		}
	}

	@ExceptionHandler(value = HystrixTimeoutException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result hystrixRuntimeExceptionHandler(final HystrixTimeoutException ex){
		log.error("捕获hystrix超时异常", ex);
		return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务间超时。");
	}

	@ExceptionHandler(value = FeignException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result feignExceptionHandler(final FeignException ex){
		log.error("捕获feign异常", ex);
		return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务调用异常。");
	}
}
