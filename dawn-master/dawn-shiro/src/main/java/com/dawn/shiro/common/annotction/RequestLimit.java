package com.dawn.shiro.common.annotction;

import com.dawn.common.enums.ErrorCodeEnum;

import java.lang.annotation.*;

/**
 * ---------------------------
 * 请求限制，接口防刷 (RequestLimit)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {

    /**
     * 默认为5秒内可访问1次
     */
    int second() default 5;

    int maxCount() default 1;

    /**
     * 超出限制后，返回的错误提示
     * @return msg
     */
    ErrorCodeEnum msg() default ErrorCodeEnum.A0501;

}
