package com.dawn.shiro.common.constant;

/**
 * ---------------------------
 * 常量类 (Constant)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
public class Constant {

    /**
     * 验证码过期时间 此处为五分钟 5 * 60
     */
    public static Integer CODE_EXPIRE_TIME = 300;

    /**
     * jwtToken过期时间 2小时
     */
    public static Integer TOKEN_EXPIRE_TIME_HOUR = 2;

    /**
     * token请求头名称   请求的Headers需带
     */
    public static String TOKEN_HEADER_NAME = "X-Token";

    /**
     * 表单重复提交间隔时间 单位 S
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    public static int FORM_REPEAT_TIME = 10;

    /**
     * 基础散列算法
     */
    public static final String ALGORITHM_NAME = "SHA-256";

    /**
     * 自定义散列次数
     */
    public static final int HASH_ITERATIONS = 1024;

}
