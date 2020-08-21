package com.dawn.shiro.common.constant;

/**
 * ---------------------------
 * Redis Key前缀 (RedisKey)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
public class RedisKey {

    public static String getLoginCodeKey(String phone){
        return "LOGIN:CODE:" + phone;
    }

    public static String getModifyPasswordCodeKey(String phone){
        return "MODIFY:PASSWORD:CODE:" + phone;
    }

    public static String getLoginUserKey(String phone){
        return "LOGIN:USER:" + phone;
    }

    public static String getFormRepeatKey(){
        return "REPEAT:DATA";
    }

    public static String getRequestLimitKey(String servletPath, String phone){
        return "LIMIT:" + servletPath + ":" + phone;
    }

}
