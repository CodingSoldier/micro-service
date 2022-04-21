package com.github.codingsoldier.starter.openfeign;

import com.github.codingsoldier.starter.openfeign.advice.FeignExceptionHandlerAdvice;
import com.github.codingsoldier.starter.openfeign.annotation.ConditionalOnStarterOpenFeignEnabled;
import com.github.codingsoldier.starter.openfeign.config.FeignConfig;
import com.github.codingsoldier.starter.openfeign.config.FeignOkHttpConfig;
import org.springframework.context.annotation.Import;


@ConditionalOnStarterOpenFeignEnabled
@Import({FeignConfig.class, FeignOkHttpConfig.class, FeignExceptionHandlerAdvice.class})
public class FeignAutoConfiguration {
}
