package com.github.codingsoldier.starter.openfeign;

import com.github.codingsoldier.starter.openfeign.advice.FeignExceptionHandlerAdvice;
import com.github.codingsoldier.starter.openfeign.annotation.ConditionalOnStarterOpenFeignEnabled;
import com.github.codingsoldier.starter.openfeign.config.FeignConfig;
import org.springframework.context.annotation.Import;

/**
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@ConditionalOnStarterOpenFeignEnabled
@Import({FeignConfig.class, FeignExceptionHandlerAdvice.class})
public class FeignAutoConfiguration {
}
