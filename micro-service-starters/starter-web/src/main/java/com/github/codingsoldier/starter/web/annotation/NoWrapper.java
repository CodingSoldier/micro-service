package com.github.codingsoldier.starter.web.annotation;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NoWrapper {

    /**
     * 是否不包转
     *
     * @return true不包装
     */
    boolean value() default true;
}
