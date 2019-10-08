package com.dawn.common.annotation;

import java.lang.annotation.*;

/**
 * ---------------------------
 * 自定义版本号注解
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-29 17:30:00
 * ---------------------------
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {
    int value() default 1;
}
