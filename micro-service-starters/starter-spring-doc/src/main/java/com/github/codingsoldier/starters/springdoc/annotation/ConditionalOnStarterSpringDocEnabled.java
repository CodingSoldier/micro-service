package com.github.codingsoldier.starters.springdoc.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否启用 starter-spring-doc
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@ConditionalOnProperty(value = "framework.starter.spring-doc.enabled", matchIfMissing = true)
public @interface ConditionalOnStarterSpringDocEnabled {

}
