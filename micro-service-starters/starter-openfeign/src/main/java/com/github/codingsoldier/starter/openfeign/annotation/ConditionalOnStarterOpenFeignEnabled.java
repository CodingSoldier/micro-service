package com.github.codingsoldier.starter.openfeign.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否启用 starter-openfeign
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProperty(value = "micro-service.starter.openfeign.enabled", matchIfMissing = true)
public @interface ConditionalOnStarterOpenFeignEnabled {

}
