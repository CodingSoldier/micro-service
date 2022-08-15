package com.github.codingsoldier.starter.web.annotation;

import java.lang.annotation.*;

/**
 * controller加上 @NoWrapper 的方法，不包装返回值
 *
 * @author cpq
 * @since 2022-03-17 11:28:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoWrapper {

    /**
     * 是否不包装
     *
     * @return true不包装
     */
    boolean value() default true;
}
