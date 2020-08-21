package com.dawn.shiro.common.enums;

/**
 * ---------------------------
 * 登录类型 (LoginType)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
public enum LoginType {

    /**
     * 密码登录
     */
    PASSWORD_LOGIN_TYPE("Password"),
    /**
     * 验证码登录
     */
    CODE_LOGIN_TYPE("Code");

    private String type;

    LoginType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

}
