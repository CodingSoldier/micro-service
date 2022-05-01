package com.github.codingsoldier.starter.openfeign.advice;


import com.github.codingsoldier.common.enums.ResponseCodeEnum;
import com.github.codingsoldier.common.resp.Result;
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
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class FeignExceptionHandlerAdvice {

	@ExceptionHandler(value = FeignException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result feignExceptionHandler(final FeignException ex){
		log.error("捕获feign异常", ex);
		return Result.fail(ResponseCodeEnum.BAD_REQUEST.getCode(), "服务调用异常。");
	}
}
