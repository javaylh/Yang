package com.dawn.shiro.common.annotction;

import java.lang.annotation.*;

/**
 * ---------------------------
 * 自定义注解防止表单重复提交 (RepeatSubmit)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

}
